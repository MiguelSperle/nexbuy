package com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence.jpa.entities.JpaCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaCategoryRepository extends JpaRepository<JpaCategoryEntity, String>, JpaSpecificationExecutor<JpaCategoryEntity> {
    @Query(nativeQuery = true, value = "SELECT EXISTS (SELECT 1 FROM categories c WHERE LOWER(c.name) = LOWER(:name))")
    boolean existsByName(@Param("name") String name);
}
