package io.github.onlineshop.orders.domain;

import io.github.onlineshop.products.domain.Product;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLine {
    private Long id;
    private Product product;
    private BigDecimal priceAtTime;
    private Long quantity;
    private Order order;
}
