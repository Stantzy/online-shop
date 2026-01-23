package io.github.onlineshop.orders.api.dto;

import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
public class OrderAddToCartResponse {
    Long cartId;
    Long totalItems;
    BigDecimal totalPrice;
    List<OrderLineDto> cartItems;
}
