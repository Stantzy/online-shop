package io.github.onlineshop.orders.domain;

import io.github.onlineshop.orders.OrderLineMapper;
import io.github.onlineshop.orders.OrderMapper;
import io.github.onlineshop.orders.OrderStatus;
import io.github.onlineshop.orders.api.dto.OrderAddToCartRequest;
import io.github.onlineshop.orders.api.dto.OrderAddToCartResponse;
import io.github.onlineshop.orders.api.dto.OrderDto;
import io.github.onlineshop.orders.database.OrderEntity;
import io.github.onlineshop.orders.database.OrderLineEntity;
import io.github.onlineshop.orders.database.OrderLineRepository;
import io.github.onlineshop.orders.database.OrderRepository;
import io.github.onlineshop.orders.domain.exception.InsufficientStockException;
import io.github.onlineshop.products.ProductMapper;
import io.github.onlineshop.products.api.dto.ProductDto;
import io.github.onlineshop.products.database.ProductEntity;
import io.github.onlineshop.products.database.ProductRepository;
import io.github.onlineshop.products.domain.Product;
import io.github.onlineshop.security.jwt.JwtTokenUtils;
import io.github.onlineshop.users.UserMapper;
import io.github.onlineshop.users.database.UserEntity;
import io.github.onlineshop.users.database.UserRepository;
import io.github.onlineshop.users.domain.User;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.naming.InsufficientResourcesException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private static final Logger log =
        LoggerFactory.getLogger(OrderService.class);

    private final OrderMapper orderMapper;
    private final OrderLineMapper orderLineMapper;
    private final ProductMapper productMapper;
    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProductRepository productRepository;
    private final JwtTokenUtils jwtTokenUtils;

    public OrderService(
        OrderRepository orderRepository,
        OrderLineRepository orderLineRepository,
        OrderMapper orderMapper,
        UserRepository userRepository,
        UserMapper userMapper,
        ProductRepository productRepository,
        ProductMapper productMapper,
        OrderLineMapper orderLineMapper,
        JwtTokenUtils jwtTokenUtils
    ) {
        this.orderRepository = orderRepository;
        this.orderLineRepository = orderLineRepository;
        this.orderMapper = orderMapper;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.orderLineMapper = orderLineMapper;
        this.jwtTokenUtils = jwtTokenUtils;
    }

    public List<OrderDto> getAllOrders() {
        log.info("Called method getAllOrders");

        List<OrderEntity> orderEntities = orderRepository.findAll();

        return orderEntities.stream()
            .map(orderMapper::toOrderDto)
            .toList();
    }

    public OrderDto getOrderById(Long id) {
        log.info("Called method getOrderById: id={}", id);

        OrderEntity orderEntity = orderRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(
                "Not found order by id = " + id)
            );

        return orderMapper.toOrderDto(orderEntity);
    }

    // TODO need more informative logs
    public OrderAddToCartResponse addItemToCart(
        OrderAddToCartRequest request,
        String authHeader
    ) {
        log.info(
            "Called method addItemToCart: productId={}, quantity={}",
            request.productId(), request.quantity()
        );

        Long userId = getUserIdFromAuthHeader(authHeader);

        // getUserOrThrow()
        UserEntity userEntity = userRepository.findById(userId)
            .orElseThrow(
                () -> new EntityNotFoundException(
                    "Not found user by id=" + userId
                )
            );
        User userDomain = userMapper.toDomainUser(userEntity);

        Order cart = findOrCreateCart(userEntity);

        // getProductOrThrow()
        ProductEntity productEntity =
            productRepository.findById(request.productId())
                .orElseThrow(
                    () -> new EntityNotFoundException(
                        "Not found product with id=" + request.productId()
                    )
                );
        Product domainProduct = productMapper.toDomainProduct(productEntity);

        domainProduct.checkProductAvailability(request.quantity());

        OrderLine orderLine = new OrderLine(
            null,
            domainProduct,
            domainProduct.getPrice(),
            request.quantity(),
            cart
        );

        List<OrderLine> lines = new ArrayList<>(cart.getOrderLines());
        lines.add(orderLine);
        cart.setOrderLines(lines);

        domainProduct.decreaseQuantity(request.quantity());

        orderRepository.save(orderMapper.toOrderEntity(cart, userEntity));
        productRepository.save(productMapper.toProductEntity(domainProduct));

        List<ProductDto> productDtoList = cart.getOrderLines().stream()
            .map(OrderLine::getProduct)
            .map(productMapper::toProductDto)
            .toList();

        return new OrderAddToCartResponse(
            cart.getId(),
            cart.getTotalItems(),
            cart.getTotalPrice(),
            productDtoList
        );
    }

    private Order findOrCreateCart(UserEntity userEntity) {
        Optional<OrderEntity> cartEntityOptional =
            orderRepository.findCartByUserId(userEntity.getId());

        if(cartEntityOptional.isEmpty()) {
            OrderEntity cartEntity = new OrderEntity();

            cartEntity.setId(null);
            cartEntity.setUserEntity(userEntity);
            cartEntity.setOrderStatus(OrderStatus.CART);
            cartEntity.setOrderLines(new ArrayList<>());
            cartEntity = orderRepository.save(cartEntity);

            return orderMapper.toDomainOrder(cartEntity);
        }

        return orderMapper.toDomainOrder(cartEntityOptional.get());
    }

    // FIXME move this method to utils(1) or security(2)
    private Long getUserIdFromAuthHeader(String authHeader) {
        final String BEARER = "Bearer ";
        String token = null;

        log.info("getUserIdFromAuthHeader: authHeader={}", authHeader);
        if(authHeader != null && authHeader.startsWith(BEARER))
            token = authHeader.substring(BEARER.length());

        log.info("getUserIdFromAuthHeader: token={}", token);

        String userId = jwtTokenUtils.getId(token);

        return Long.parseLong(userId);
    }

    public void deleteOrderById(Long id) {
        log.info("Called method deleteOrderById: id={}", id);
        orderRepository.deleteById(id);
    }

    // TODO approveOrder()
    // TODO getOrdersByUser() / getOrdersByUserId()
    // TODO changeOrderStatus()
}
