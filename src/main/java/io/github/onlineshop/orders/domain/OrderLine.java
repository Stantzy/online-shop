package io.github.onlineshop.orders.domain;

import io.github.onlineshop.products.domain.Product;

import java.math.BigDecimal;

public class OrderLine {
    private Long id;
    private Product product;
    private BigDecimal priceAtTime;
    private Long quantity;
    private Order order;

    public OrderLine() {}

    public OrderLine(
        Long id,
        Product product,
        BigDecimal priceAtTime,
        Long quantity,
        Order order
    ) {
        this.id = id;
        this.product = product;
        this.priceAtTime = priceAtTime;
        this.quantity = quantity;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getPriceAtTime() {
        return priceAtTime;
    }

    public void setPriceAtTime(BigDecimal priceAtTime) {
        this.priceAtTime = priceAtTime;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
