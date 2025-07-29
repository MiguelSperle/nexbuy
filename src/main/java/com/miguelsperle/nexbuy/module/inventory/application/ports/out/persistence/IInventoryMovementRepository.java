package com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence;

import com.miguelsperle.nexbuy.module.inventory.domain.entities.InventoryMovement;

import java.util.List;
import java.util.Optional;

public interface IInventoryMovementRepository {
    List<InventoryMovement> findAll();
    Optional<InventoryMovement> findById(String id);
    InventoryMovement save(InventoryMovement inventoryMovement);
    void deleteById(String id);
}
