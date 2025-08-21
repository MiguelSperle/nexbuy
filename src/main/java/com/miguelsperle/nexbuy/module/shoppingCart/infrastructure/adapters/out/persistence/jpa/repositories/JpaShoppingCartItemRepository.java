package com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.out.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.out.persistence.jpa.entities.JpaShoppingCartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaShoppingCartItemRepository extends JpaRepository<JpaShoppingCartItemEntity, String> {
    @Query(nativeQuery = true, value = "SELECT * FROM shopping_cart_items sct WHERE sct.shopping_cart_id = :shoppingCartId AND sct.product_id = :productId")
    Optional<JpaShoppingCartItemEntity> findByShoppingCartIdAndProductId(@Param("shoppingCartId") String shoppingCartId, @Param("productId") String productId);

    @Query(nativeQuery = true, value = "SELECT * FROM shopping_cart_items sct WHERE sct.shopping_cart_id = :shoppingCartId")
    List<JpaShoppingCartItemEntity> findAllByShoppingCartId(@Param("shoppingCartId") String shoppingCartId);

    @Query(nativeQuery = true, value = "SELECT * FROM shopping_cart_items sct WHERE sct.product_id = :productId")
    List<JpaShoppingCartItemEntity> findAllByProductId(@Param("productId") String productId);
}
