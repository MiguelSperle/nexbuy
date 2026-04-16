package com.miguelsperle.nexbuy.module.inventory.infrastructure.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.inventory.infrastructure.persistence.jpa.entities.JpaInventoryMovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JpaInventoryMovementRepository extends JpaRepository<JpaInventoryMovementEntity, String>, JpaSpecificationExecutor<JpaInventoryMovementEntity> {
}
