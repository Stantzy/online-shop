package io.github.onlineshop.orders.api.dto;

import io.github.onlineshop.products.domain.Product;

import java.math.BigDecimal;

public record OrderLineDto(
    Long id,
    Long productId,
    BigDecimal priceAtTime,
    Long quantity
) {

}
