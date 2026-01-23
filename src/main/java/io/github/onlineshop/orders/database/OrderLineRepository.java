package io.github.onlineshop.orders.database;

import io.github.onlineshop.products.database.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLineEntity, Long> {
    boolean existsByProductEntity(ProductEntity productEntity);
}
