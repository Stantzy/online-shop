package io.github.onlineshop.orders.api.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class OrderLineDto {
    Long id;
    Long productId;
    BigDecimal priceAtTime;
    Long quantity;
}
