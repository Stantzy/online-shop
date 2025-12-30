package io.github.onlineshop.orders.domain;

import io.github.onlineshop.orders.OrderStatus;
import io.github.onlineshop.orders.database.OrderLineEntity;
import io.github.onlineshop.users.database.UserEntity;

import java.util.List;

public class Order {
    private Long id;
    private OrderStatus orderStatus;
    private UserEntity userEntity;
    private List<OrderLineEntity> products;

    public Order() {
    }

    public Order(
        Long id,
        OrderStatus orderStatus,
        UserEntity userEntity,
        List<OrderLineEntity> products
    ) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.userEntity = userEntity;
        this.products = products;
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

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public List<OrderLineEntity> getProducts() {
        return products;
    }

    public void setProducts(List<OrderLineEntity> products) {
        this.products = products;
    }
}
