package com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.out.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.out.persistence.jpa.entities.JpaInventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaInventoryRepository extends JpaRepository<JpaInventoryEntity, String>, JpaSpecificationExecutor<JpaInventoryEntity> {
    @Query(nativeQuery = true, value = "SELECT * FROM inventories i WHERE i.sku = :sku")
    Optional<JpaInventoryEntity> findBySku(@Param("sku") String sku);
}
