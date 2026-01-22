package io.github.onlineshop.orders.api.dto;

import java.math.BigDecimal;

public record OrderLineDto(
    Long id,
    Long productId,
    BigDecimal priceAtTime,
    Long quantity
) {

}
