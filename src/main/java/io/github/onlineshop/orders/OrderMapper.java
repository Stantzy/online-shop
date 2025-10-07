package io.github.onlineshop.orders;

import io.github.onlineshop.orders.api.dto.OrderDto;
import io.github.onlineshop.orders.database.OrderEntity;
import io.github.onlineshop.products.database.ProductEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {
    public OrderDto toOrderDto(OrderEntity orderEntity) {
        List<Long> productIds = orderEntity.getProducts()
                .stream()
                .map(ProductEntity::getId)
                .toList();

        return new OrderDto(
                orderEntity.getOrderOwner().getId(),
                productIds,
                orderEntity.getOrderStatus()
        );
    }

    public OrderEntity toOrderEntity(OrderDto orderDto) {
        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setId(null);
        orderEntity.setUserId(orderDto.userId());
        orderEntity.setOrderStatus(orderDto.orderStatus());

        return orderEntity;
    }
}
