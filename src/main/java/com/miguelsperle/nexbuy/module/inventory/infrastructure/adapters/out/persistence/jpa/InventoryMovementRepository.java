package com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.out.persistence.jpa;

import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.IInventoryMovementRepository;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.InventoryMovement;
import com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.out.persistence.jpa.entities.JpaInventoryMovementEntity;
import com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.out.persistence.jpa.repositories.JpaInventoryMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class InventoryMovementRepository implements IInventoryMovementRepository {
    private final JpaInventoryMovementRepository jpaInventoryMovementRepository;

    @Override
    public List<InventoryMovement> findAll() {
        return this.jpaInventoryMovementRepository.findAll().stream().map(JpaInventoryMovementEntity::toEntity).toList();
    }

    @Override
    public Optional<InventoryMovement> findById(String id) {
        return this.jpaInventoryMovementRepository.findById(id).map(JpaInventoryMovementEntity::toEntity);
    }

    @Override
    public InventoryMovement save(InventoryMovement inventoryMovement) {
        return this.jpaInventoryMovementRepository.save(JpaInventoryMovementEntity.from(inventoryMovement)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaInventoryMovementRepository.deleteById(id);
    }
}
