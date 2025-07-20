package com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.entities.JpaBrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaBrandRepository extends JpaRepository<JpaBrandEntity, String> {
    @Query(nativeQuery = true, value = "SELECT EXISTS (SELECT 1 FROM brands b WHERE LOWER(b.name) = LOWER(:name))")
    boolean existsByName(@Param("name") String name);

    @Query(nativeQuery = true, value = "SELECT * FROM brands WHERE id IN (:ids)")
    List<JpaBrandEntity> findAllByIds(@Param("ids") List<String> ids);
}
