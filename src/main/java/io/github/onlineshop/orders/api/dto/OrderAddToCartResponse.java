package io.github.onlineshop.orders.api.dto;

import java.math.BigDecimal;
import java.util.List;

public record OrderAddToCartResponse(
    Long cartId,
    Long totalItems,
    BigDecimal totalPrice,
    List<OrderLineDto> cartItems
) {

}
