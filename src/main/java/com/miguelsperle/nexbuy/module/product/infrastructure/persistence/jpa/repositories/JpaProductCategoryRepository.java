package com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.entities.JpaProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaProductCategoryRepository extends JpaRepository<JpaProductCategoryEntity, String> {
    @Query(nativeQuery = true, value = "SELECT * FROM product_categories pc WHERE LOWER(pc.name) = LOWER(:name)")
    Optional<JpaProductCategoryEntity> findByName(@Param("name" ) String name);
}
