package com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.entities.JpaCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaCategoryRepository extends JpaRepository<JpaCategoryEntity, String> {
    @Query(nativeQuery = true, value = "SELECT * FROM categories c WHERE LOWER(c.name) = LOWER(:name)")
    Optional<JpaCategoryEntity> findByName(@Param("name") String name);
}
