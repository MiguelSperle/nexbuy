package com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.entities.JpaBrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaBrandRepository extends JpaRepository<JpaBrandEntity, String> {
    @Query(nativeQuery = true, value = "SELECT * FROM brands b WHERE LOWER(b.name) = LOWER(:name)")
    Optional<JpaBrandEntity> findByName(@Param("name") String name);
}
