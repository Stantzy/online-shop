package io.github.onlineshop.products.domain;

import io.github.onlineshop.orders.api.dto.OrderAddToCartRequest;
import io.github.onlineshop.orders.domain.exception.InsufficientStockException;

import java.math.BigDecimal;

public class Product {
    private Long id;
    private String name;
    private Long quantity;
    private BigDecimal price;

    public Product() {}

    public Product(Long id, String name, Long quantity, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void checkProductAvailability(
        Long requestedQuantity
    ) {
        if(quantity < requestedQuantity) {
            throw new InsufficientStockException(
                id,
                name,
                requestedQuantity,
                quantity
            );
        }
    }

    public void decreaseQuantity(Long amount) {
        if(amount > this.quantity) {
            throw new InsufficientStockException(
                "Cannot decrease more than available"
            );
        }
        this.quantity -= amount;
    }
}
