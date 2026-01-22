package io.github.onlineshop.orders.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderAddToCartRequest(
    @NotNull
    @Positive
    Long productId,

    @NotNull
    @Positive
    Long quantity
) {

}
