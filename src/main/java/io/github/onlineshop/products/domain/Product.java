package io.github.onlineshop.products.domain;

import java.math.BigDecimal;

public record Product(
        Long id,
        String name,
        Long quantity,
        BigDecimal price
) {
}
