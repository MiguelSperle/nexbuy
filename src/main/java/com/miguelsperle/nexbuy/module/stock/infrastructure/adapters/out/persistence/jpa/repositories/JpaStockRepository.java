package com.miguelsperle.nexbuy.module.stock.infrastructure.adapters.out.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.stock.infrastructure.adapters.out.persistence.jpa.entities.JpaStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaStockRepository extends JpaRepository<JpaStockEntity, String> {
}
