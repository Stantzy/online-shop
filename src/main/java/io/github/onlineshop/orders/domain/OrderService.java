package io.github.onlineshop.orders.domain;

import io.github.onlineshop.orders.OrderMapper;
import io.github.onlineshop.orders.api.dto.OrderDto;
import io.github.onlineshop.orders.database.OrderEntity;
import io.github.onlineshop.orders.database.OrderRepostitory;
import io.github.onlineshop.products.database.ProductEntity;
import io.github.onlineshop.products.database.ProductRepository;
import io.github.onlineshop.users.database.UserEntity;
import io.github.onlineshop.users.database.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private static final Logger log =
            LoggerFactory.getLogger(OrderService.class);
    private final OrderMapper mapper;
    private final OrderRepostitory orderRepostitory;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderService(
            OrderRepostitory orderRepostitory,
            UserRepository userRepository,
            ProductRepository productRepository,
            OrderMapper mapper
    ) {
        this.orderRepostitory = orderRepostitory;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    public List<OrderDto> getAllOrders() {
        log.info("Called method getAllOrders");

        List<OrderEntity> orderEntities = orderRepostitory.findAll();
        return orderEntities.stream()
                .map(mapper::toOrderDto)
                .toList();
    }

    public OrderDto getOrderById(Long id) {
        log.info("Called method getOrderById: id={}", id);

        OrderEntity orderEntity = orderRepostitory.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Not found order by id = " + id)
                );

        return mapper.toOrderDto(orderEntity);
    }

    public OrderDto createOrder(OrderDto orderToCreate) {
        log.info("Called method createOrder");

        OrderEntity orderEntityToSave = mapper.toOrderEntity(orderToCreate);

        orderEntityToSave.setUserId(orderEntityToSave.getUserId());
        orderEntityToSave.setOrderStatus(orderToCreate.orderStatus());

        Long userId = orderEntityToSave.getUserId();
        UserEntity orderOwner = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Not found user by id = " + userId)
                );
        orderEntityToSave.setOrderOwner(orderOwner);

        List<ProductEntity> orderItems =
                productRepository.findAllById(orderToCreate.productIds());
        orderEntityToSave.setProducts(orderItems);

        return mapper.toOrderDto(orderRepostitory.save(orderEntityToSave));
    }

    public void deleteOrderById(Long id) {
        log.info("Called method deleteOrderById: id={}", id);
        orderRepostitory.deleteById(id);
    }
}
