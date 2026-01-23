package io.github.onlineshop.orders.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Value;

@Value
public class OrderAddToCartRequest {
    @NotNull
    @Positive
    Long productId;

    @NotNull
    @Positive
    Long quantity;
}
