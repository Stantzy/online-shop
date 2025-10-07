package io.github.onlineshop.orders.api.dto;

import io.github.onlineshop.orders.OrderStatus;

import java.util.List;

public record OrderDto(
    Long userId,
    List<Long> productIds,
    OrderStatus orderStatus
) {

}
