package io.github.onlineshop.orders.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepostitory extends JpaRepository<OrderEntity, Long> {
}
