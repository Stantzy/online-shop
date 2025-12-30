package io.github.onlineshop.orders.domain;

import io.github.onlineshop.orders.OrderMapper;
import io.github.onlineshop.orders.OrderStatus;
import io.github.onlineshop.orders.api.dto.OrderAddToCartRequest;
import io.github.onlineshop.orders.api.dto.OrderAddToCartResponse;
import io.github.onlineshop.orders.api.dto.OrderDto;
import io.github.onlineshop.orders.database.OrderEntity;
import io.github.onlineshop.orders.database.OrderRepository;
import io.github.onlineshop.products.database.ProductEntity;
import io.github.onlineshop.products.database.ProductRepository;
import io.github.onlineshop.products.domain.Product;
import io.github.onlineshop.security.jwt.JwtTokenUtils;
import io.github.onlineshop.users.database.UserEntity;
import io.github.onlineshop.users.database.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private static final Logger log =
        LoggerFactory.getLogger(OrderService.class);

    private final OrderMapper mapper;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final JwtTokenUtils jwtTokenUtils;

    public OrderService(
        OrderRepository orderRepository,
        UserRepository userRepository,
        ProductRepository productRepository,
        OrderMapper mapper,
        JwtTokenUtils jwtTokenUtils
    ) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
        this.jwtTokenUtils = jwtTokenUtils;
    }

    public List<OrderDto> getAllOrders() {
        log.info("Called method getAllOrders");

        List<OrderEntity> orderEntities = orderRepository.findAll();

        return orderEntities.stream()
            .map(mapper::toOrderDto)
            .toList();
    }

    public OrderDto getOrderById(Long id) {
        log.info("Called method getOrderById: id={}", id);

        OrderEntity orderEntity = orderRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(
                "Not found order by id = " + id)
            );

        return mapper.toOrderDto(orderEntity);
    }

    // TODO Do I need another methods called "createCart()" and "addItemToCart()"?
    public OrderAddToCartResponse addItemToCart(
        OrderAddToCartRequest request,
        String authHeader
    ) {
        log.info("Called method addItemToCart");

        final String BEARER = "Bearer ";
        String token = null;

        if(authHeader != null && authHeader.startsWith(BEARER))
            token = authHeader.substring(BEARER.length());

        String userId = jwtTokenUtils.getId(token);
        List<OrderEntity> userOrders =
            orderRepository.findByUserId(Long.getLong(userId));

        Order cart = getCartFromOrders(userOrders);

        if(cart == null) {
            cart = new Order();
            cart.setOrderStatus(OrderStatus.CART);
        }

        Optional<ProductEntity> product =
            productRepository.findById(request.productId());

        if(product.isEmpty()) {
            throw new IllegalArgumentException(
                "Wrong product id=" + request.productId()
            );
        }

        // TODO create OrderLine and put into it current product
        // TODO and save all to the database

        return null;
    }

    private Order getCartFromOrders(List<OrderEntity> orderEntities) {
        Order cart = null;

        for(OrderEntity orderEntity : orderEntities) {
            if(orderEntity.getOrderStatus() == OrderStatus.CART) {
                cart = mapper.toDomainOrder(orderEntity);
                break;
            }
        }

        return cart;
    }

    public OrderDto getCart(Long userId) {

    }

    // TODO createOrderFromCart()

    // TODO is OrderStatus.CART exist? yes: add to CART, no: create CART and add
    // TODO must create OrderLine and add to Order. need to delete productEntity
//    public OrderDto createOrder(OrderDto orderToCreate) {
//        log.info("Called method createOrder");
//
//        OrderEntity orderEntityToSave = mapper.toOrderEntity(orderToCreate);
//
//        orderEntityToSave.setUserId(orderEntityToSave.getUserId());
//        orderEntityToSave.setOrderStatus(orderToCreate.orderStatus());
//
//        Long userId = orderEntityToSave.getUserId();
//        UserEntity orderOwner = userRepository.findById(userId)
//            .orElseThrow(() -> new EntityNotFoundException(
//                "Not found user by id = " + userId)
//            );
//
//        orderEntityToSave.setOrderOwner(orderOwner);
//
//        List<ProductEntity> orderItems =
//            productRepository.findAllById(orderToCreate.productIds());
//
//        orderEntityToSave.setProducts(orderItems);
//
//        return mapper.toOrderDto(orderRepository.save(orderEntityToSave));
//    }

    public void deleteOrderById(Long id) {
        log.info("Called method deleteOrderById: id={}", id);
        orderRepository.deleteById(id);
    }

    // TODO getOrdersByUser() / getOrdersByUserId()?

    // TODO changeOrderStatus()?
}
