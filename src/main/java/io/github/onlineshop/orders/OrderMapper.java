package io.github.onlineshop.orders;

import io.github.onlineshop.orders.api.dto.OrderDto;
import io.github.onlineshop.orders.database.OrderEntity;
import io.github.onlineshop.orders.database.OrderLineEntity;
import io.github.onlineshop.orders.domain.Order;
import io.github.onlineshop.products.database.ProductEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {
    public OrderDto toOrderDto(OrderEntity orderEntity) {
        List<Long> productIds = orderEntity.getOrderLines()
            .stream()
            .map(OrderLineEntity::getId)
            .toList();

        return new OrderDto(
            orderEntity.getUserEntity().getId(),
            productIds,
            orderEntity.getOrderStatus()
        );
    }

    public OrderEntity toOrderEntity(OrderDto orderDto) {
        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setId(null);
        // FIXME OrderEntity does not store user ID, need to solve this
//        orderEntity.setUserId(orderDto.userId());
        orderEntity.setOrderStatus(orderDto.orderStatus());

        return orderEntity;
    }

    public Order toDomainOrder(OrderEntity orderEntity) {
        return new Order(
            orderEntity.getId(),
            orderEntity.getOrderStatus(),
            orderEntity.getUserEntity(),
            orderEntity.getOrderLines()
        );
    }
}
