package com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.entities.JpaCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaCategoryRepository extends JpaRepository<JpaCategoryEntity, String> {
    @Query(nativeQuery = true, value = "SELECT EXISTS (SELECT 1 FROM categories c WHERE LOWER(c.name) = LOWER(:name))")
    boolean existsByName(@Param("name") String name);

    @Query(nativeQuery = true, value = "SELECT * FROM categories c WHERE c.parent_category_id IS NULL")
    List<JpaCategoryEntity> findAllByParentCategoryIdNull();

    @Query(nativeQuery = true, value = "SELECT * FROM categories c WHERE c.parent_category_id IS NOT NULL")
    List<JpaCategoryEntity> findAllByParentCategoryIdNotNull();
}
