package com.miguelsperle.nexbuy.module.stock.application.ports.out.persistence;

import com.miguelsperle.nexbuy.module.stock.domain.entities.StockMovement;

import java.util.List;
import java.util.Optional;

public interface IStockMovementRepository {
    List<StockMovement> findAll();
    Optional<StockMovement> findById(String id);
    StockMovement save(StockMovement stockMovement);
    void deleteById(String id);
}
