package com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.out.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.out.persistence.jpa.entities.JpaShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaShoppingCartRepository extends JpaRepository<JpaShoppingCartEntity, String> {
    @Query(nativeQuery = true, value = "SELECT * FROM shopping_carts sc WHERE sc.user_id = :userId")
    Optional<JpaShoppingCartEntity> findByUserId(@Param("userId") String userId);
}
