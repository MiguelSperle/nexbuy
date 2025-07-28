package com.miguelsperle.nexbuy.module.stock.infrastructure.adapters.out.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.stock.infrastructure.adapters.out.persistence.jpa.entities.JpaStockMovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaStockMovementRepository extends JpaRepository<JpaStockMovementEntity, String> {
}
