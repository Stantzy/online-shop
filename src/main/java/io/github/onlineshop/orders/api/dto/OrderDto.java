package io.github.onlineshop.orders.api.dto;

import io.github.onlineshop.orders.OrderStatus;

import java.util.List;

public record OrderDto(
    Long orderId,
    Long userId,
    List<Long> orderLineIds,
    OrderStatus orderStatus
) {

}
