package io.github.onlineshop.orders.database;

import io.github.onlineshop.users.database.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByUserEntity(UserEntity userEntity);

    @Query(
        "SELECT o FROM OrderEntity o " +
        "WHERE o.userEntity.id = :userId AND o.orderStatus = 'CART'"
    )
    Optional<OrderEntity> findCartByUserId(@Param("userId")Long id);
}
