package com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.entities.JpaColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaColorRepository extends JpaRepository<JpaColorEntity, String> {
    @Query(nativeQuery = true, value = "SELECT EXISTS (SELECT 1 FROM colors c WHERE LOWER(c.name) = LOWER(:name))")
    boolean existsByName(@Param("name") String name);
}
