package com.miguelsperle.nexbuy.module.stock.infrastructure.adapters.out.persistence.jpa;

import com.miguelsperle.nexbuy.module.stock.application.ports.out.persistence.IStockMovementRepository;
import com.miguelsperle.nexbuy.module.stock.domain.entities.StockMovement;
import com.miguelsperle.nexbuy.module.stock.infrastructure.adapters.out.persistence.jpa.entities.JpaStockMovementEntity;
import com.miguelsperle.nexbuy.module.stock.infrastructure.adapters.out.persistence.jpa.repositories.JpaStockMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StockMovementRepository implements IStockMovementRepository {
    private final JpaStockMovementRepository jpaStockMovementRepository;

    @Override
    public List<StockMovement> findAll() {
        return this.jpaStockMovementRepository.findAll().stream().map(JpaStockMovementEntity::toEntity).toList();
    }

    @Override
    public Optional<StockMovement> findById(String id) {
        return this.jpaStockMovementRepository.findById(id).map(JpaStockMovementEntity::toEntity);
    }

    @Override
    public StockMovement save(StockMovement stockMovement) {
        return this.jpaStockMovementRepository.save(JpaStockMovementEntity.from(stockMovement)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaStockMovementRepository.deleteById(id);
    }
}
