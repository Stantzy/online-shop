package io.github.onlineshop.products.api.dto;

import java.math.BigDecimal;

public record ProductDto(
        String name,
        Long quantity,
        BigDecimal price
) {
}
