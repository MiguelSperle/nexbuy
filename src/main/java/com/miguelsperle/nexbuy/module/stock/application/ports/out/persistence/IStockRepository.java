package com.miguelsperle.nexbuy.module.stock.application.ports.out.persistence;

import com.miguelsperle.nexbuy.module.stock.domain.entities.Stock;

import java.util.List;
import java.util.Optional;

public interface IStockRepository {
    List<Stock> findAll();
    Optional<Stock> findById(String id);
    Stock save(Stock stock);
    void deleteById(String id);
}
