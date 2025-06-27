package com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.entities.JpaProductBrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaProductBrandRepository extends JpaRepository<JpaProductBrandEntity, String> {
    @Query(nativeQuery = true, value = "SELECT * FROM product_brands pb WHERE LOWER(pb.name) = LOWER(:name)")
    Optional<JpaProductBrandEntity> findByName(@Param("name") String name);
}
