package io.github.onlineshop.products.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    boolean existsByName(String name);
    void deleteByName(String name);
}
