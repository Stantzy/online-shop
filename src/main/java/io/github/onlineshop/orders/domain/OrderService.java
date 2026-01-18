package io.github.onlineshop.orders.domain;

import io.github.onlineshop.orders.OrderLineMapper;
import io.github.onlineshop.orders.OrderMapper;
import io.github.onlineshop.orders.OrderStatus;
import io.github.onlineshop.orders.api.dto.*;
import io.github.onlineshop.orders.database.OrderEntity;
import io.github.onlineshop.orders.database.OrderLineEntity;
import io.github.onlineshop.orders.database.OrderRepository;
import io.github.onlineshop.products.ProductMapper;
import io.github.onlineshop.products.database.ProductEntity;
import io.github.onlineshop.products.database.ProductRepository;
import io.github.onlineshop.products.domain.Product;
import io.github.onlineshop.users.UserMapper;
import io.github.onlineshop.users.database.UserEntity;
import io.github.onlineshop.users.database.UserRepository;
import io.github.onlineshop.users.domain.User;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProductRepository productRepository;

    public OrderService(
        OrderRepository orderRepository,
        OrderMapper orderMapper,
        UserRepository userRepository,
        UserMapper userMapper,
        ProductRepository productRepository,
        ProductMapper productMapper,
        OrderLineMapper orderLineMapper
    ) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.orderLineMapper = orderLineMapper;
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

    public void deleteOrderById(Long id) {
        log.info("Called method deleteOrderById: id={}", id);
        orderRepository.deleteById(id);
    }

    // TODO need more informative logs
    public OrderAddToCartResponse addItemToCart(
        OrderAddToCartRequest request,
        Long userId
    ) {
        log.info(
            "Called method addItemToCart: productId={}, quantity={}",
            request.productId(), request.quantity()
        );

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

        OrderEntity cartEntity = orderMapper.toOrderEntity(cart, userEntity);
        List<OrderLineEntity> orderLineEntityList =
            orderLineMapper.toListOfOrderLineEntities(lines, cartEntity);
        cartEntity.setOrderLines(orderLineEntityList);

        orderRepository.save(cartEntity);
        productRepository.save(productMapper.toProductEntity(domainProduct));

        List<OrderLineDto> orderLineDtoList = lines.stream()
            .map(orderLineMapper::toOrderLineDto)
            .toList();

        return new OrderAddToCartResponse(
            cart.getId(),
            cart.getTotalItems(),
            cart.getTotalPrice(),
            orderLineDtoList
        );
    }

    public OrderCartDto getCart(Long userId) {
        Order cart = findOrCreateCart(userId);
        BigDecimal totalPrice = cart.getTotalPrice();
        Long itemsQuantity = cart.getTotalItems();

        List<OrderLineDto> orderLineDtoList = cart.getOrderLines()
            .stream()
            .map(orderLineMapper::toOrderLineDto)
            .toList();

        OrderCartDto orderCartDto = new OrderCartDto();

        orderCartDto.setOrderLineDtoList(orderLineDtoList);
        orderCartDto.setTotalPrice(totalPrice);
        orderCartDto.setItemsQuantity(itemsQuantity);

        return orderCartDto;
    }

    private Order findOrCreateCart(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
            .orElseThrow(
                () -> new EntityNotFoundException(
                    "Not found user by id = " + userId
                )
            );

        return findOrCreateCart(userEntity);
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

    // TODO approveOrder()
    // TODO getOrdersByUser() / getOrdersByUserId()
    // TODO changeOrderStatus()
}
