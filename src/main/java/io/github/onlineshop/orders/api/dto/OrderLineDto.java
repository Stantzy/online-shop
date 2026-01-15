package io.github.onlineshop.orders.api.dto;

import io.github.onlineshop.products.domain.Product;

import java.math.BigDecimal;

public record OrderLineDto(
    Long id,
    Product product,
    BigDecimal priceAtTime,
    Long quantity
) {

}
