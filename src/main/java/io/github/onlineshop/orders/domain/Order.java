package io.github.onlineshop.orders.domain;

import io.github.onlineshop.orders.OrderStatus;
import io.github.onlineshop.users.domain.User;

import java.math.BigDecimal;
import java.util.List;

public class Order {
    private Long id;
    private OrderStatus orderStatus;
    private User user;
    private List<OrderLine> orderLines;

    public Order() {
    }

    public Order(
        Long id,
        OrderStatus orderStatus,
        User user,
        List<OrderLine> orderLines
    ) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.user = user;
        this.orderLines = orderLines;
    }

    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = BigDecimal.ZERO;

        for(OrderLine line : orderLines) {
            BigDecimal productPrice = line.getPriceAtTime();
            BigDecimal productQuantity = BigDecimal.valueOf(line.getQuantity());
            BigDecimal lineTotalPrice = productPrice.multiply(productQuantity);
            totalPrice = totalPrice.add(lineTotalPrice) ;
        }

        return totalPrice;
    }

    public Long getTotalItems() {
        Long result = 0L;

        for(OrderLine orderLine : orderLines)
            result += orderLine.getQuantity();

        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }
}
