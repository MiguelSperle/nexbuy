package com.miguelsperle.nexbuy.module.stock.infrastructure.adapters.out.persistence.jpa;

import com.miguelsperle.nexbuy.module.stock.application.ports.out.persistence.IStockRepository;
import com.miguelsperle.nexbuy.module.stock.domain.entities.Stock;
import com.miguelsperle.nexbuy.module.stock.infrastructure.adapters.out.persistence.jpa.entities.JpaStockEntity;
import com.miguelsperle.nexbuy.module.stock.infrastructure.adapters.out.persistence.jpa.repositories.JpaStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StockRepository implements IStockRepository {
    private final JpaStockRepository jpaStockRepository;

    @Override
    public List<Stock> findAll() {
        return this.jpaStockRepository.findAll().stream().map(JpaStockEntity::toEntity).toList();
    }

    @Override
    public Optional<Stock> findById(String id) {
        return this.jpaStockRepository.findById(id).map(JpaStockEntity::toEntity);
    }

    @Override
    public Stock save(Stock stock) {
        return this.jpaStockRepository.save(JpaStockEntity.from(stock)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaStockRepository.deleteById(id);
    }
}
