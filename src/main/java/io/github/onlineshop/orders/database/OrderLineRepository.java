package io.github.onlineshop.orders.database;

import io.github.onlineshop.products.database.ProductEntity;
import io.github.onlineshop.products.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<OrderLineEntity, Long> {
    boolean existsByProductEntity(ProductEntity productEntity);
}
