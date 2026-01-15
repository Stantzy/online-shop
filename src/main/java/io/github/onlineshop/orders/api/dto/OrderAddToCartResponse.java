package io.github.onlineshop.orders.api.dto;

import io.github.onlineshop.products.api.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

public record OrderAddToCartResponse(
    Long cartId,
    Long totalItems,
    BigDecimal totalPrice,
    List<OrderLineDto> cartItems
) {

}
