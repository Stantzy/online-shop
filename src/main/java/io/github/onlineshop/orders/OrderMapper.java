package io.github.onlineshop.orders;

import io.github.onlineshop.orders.api.dto.OrderDto;
import io.github.onlineshop.orders.database.OrderEntity;
import io.github.onlineshop.orders.database.OrderLineEntity;
import io.github.onlineshop.orders.domain.Order;
import io.github.onlineshop.orders.domain.OrderLine;
import io.github.onlineshop.users.database.UserEntity;
import io.github.onlineshop.users.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {
    private final OrderLineMapper orderLineMapper;

    public OrderDto toOrderDto(Order order) {
        List<Long> productIds = order.getOrderLines()
            .stream()
            .map(OrderLine::getId)
            .toList();

        return new OrderDto(
            order.getId(),
            order.getUser().getId(),
            productIds,
            order.getOrderStatus()
        );
    }

    public OrderDto toOrderDto(OrderEntity orderEntity) {
        List<Long> productIds = orderEntity.getOrderLines()
            .stream()
            .map(OrderLineEntity::getId)
            .toList();

        return new OrderDto(
            orderEntity.getId(),
            orderEntity.getUserEntity().getId(),
            productIds,
            orderEntity.getOrderStatus()
        );
    }

    public OrderEntity toOrderEntity(
        OrderDto orderDto,
        UserEntity userEntity
    ) {
        if(orderDto == null)
            return null;

        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setId(orderDto.getOrderId());
        orderEntity.setUserEntity(userEntity);
        orderEntity.setOrderStatus(orderDto.getOrderStatus());

        return orderEntity;
    }


    public OrderEntity toOrderEntity(Order order, UserEntity userEntity) {
        if(order == null || userEntity == null)
            return null;

        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setId(order.getId());
        orderEntity.setUserEntity(userEntity);
        orderEntity.setOrderStatus(order.getOrderStatus());

        return orderEntity;
    }

    public Order toDomainOrder(OrderEntity orderEntity) {
        if(orderEntity == null)
            return null;

        // User with only ID
        User user = new User(
            orderEntity.getUserEntity().getId(),
            null, null, null, null, null, null
        );

        List<OrderLine> orderLines =
            orderLineMapper.toDomainOrderLines(
                orderEntity.getOrderLines()
            );

        return new Order(
            orderEntity.getId(),
            orderEntity.getOrderStatus(),
            user,
            orderLines
        );
    }
}
