package io.github.onlineshop.orders.domain;

import io.github.onlineshop.orders.database.OrderEntity;
import io.github.onlineshop.products.domain.Product;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

public class OrderLine {
    private Long id;
    private Product product;
    private BigDecimal priceAtTime;
    private Long quantity;
}
