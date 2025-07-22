package com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.entities.JpaProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaProductRepository extends JpaRepository<JpaProductEntity, String>, JpaSpecificationExecutor<JpaProductEntity> {
    @Query(nativeQuery = true, value = "SELECT EXISTS (SELECT 1 FROM products p WHERE p.brand_id = :brandId)")
    boolean existsByBrandId(@Param("brandId") String brandId);

    @Query(nativeQuery = true, value = "SELECT EXISTS (SELECT 1 FROM products p WHERE p.category_id = :categoryId)")
    boolean existsByCategoryId(@Param("categoryId") String categoryId);

    @Query(nativeQuery = true, value = "SELECT EXISTS (SELECT 1 FROM products p WHERE p.color_id = :colorId)")
    boolean existsByColorId(@Param("colorId") String colorId);

    @Query(nativeQuery = true, value = "SELECT * FROM products p WHERE p.id = :id AND p.status = 'ACTIVE'")
    Optional<JpaProductEntity> findActiveById(@Param("id") String id);
}
