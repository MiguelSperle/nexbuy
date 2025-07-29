package com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.out.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.out.persistence.jpa.entities.JpaInventoryMovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaInventoryMovementRepository extends JpaRepository<JpaInventoryMovementEntity, String> {
}
