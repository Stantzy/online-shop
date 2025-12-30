package io.github.onlineshop.orders.api.dto;

public record OrderAddToCartRequest(
    Long productId,
    Long quantity
) {

}
