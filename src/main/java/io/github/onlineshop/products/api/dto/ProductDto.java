package io.github.onlineshop.products.api.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductDto(
    @Null(message = "Id must be null")
    Long id,

    @NotBlank(message = "The name of the product can't be empty")
    String name,

    @NotNull(message = "Quantity must not be null")
    @PositiveOrZero(message = "Quantity can't be less than zero")
    Long quantity,

    @NotNull(message = "Price must not be null")
    @PositiveOrZero(message = "Price can't be less than zero")
    BigDecimal price
) {
}
