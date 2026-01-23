package io.github.onlineshop.orders.api.dto;

import io.github.onlineshop.orders.OrderStatus;
import lombok.Value;

import java.util.List;

@Value
public class OrderDto {
    Long orderId;
    Long userId;
    List<Long> orderLineIds;
    OrderStatus orderStatus;
}
