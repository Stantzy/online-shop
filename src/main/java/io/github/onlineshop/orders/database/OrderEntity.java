package io.github.onlineshop.orders.database;

import io.github.onlineshop.orders.OrderStatus;
import io.github.onlineshop.products.database.ProductEntity;
import io.github.onlineshop.users.database.UserEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity orderOwner;

    @Transient
    private Long userId;

    @ManyToMany
    @JoinTable(
            name = "order_items",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ProductEntity> products;

    public OrderEntity() {}

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

    public UserEntity getOrderOwner() {
        return orderOwner;
    }

    public void setOrderOwner(UserEntity orderOwner) {
        this.orderOwner = orderOwner;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }
}
