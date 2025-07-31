package com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence;

import com.miguelsperle.nexbuy.core.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.core.domain.pagination.SearchQuery;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.InventoryMovement;

import java.util.List;
import java.util.Optional;

public interface InventoryMovementRepository {
    List<InventoryMovement> findAll();
    Optional<InventoryMovement> findById(String id);
    InventoryMovement save(InventoryMovement inventoryMovement);
    void deleteById(String id);
    Pagination<InventoryMovement> findAllPaginatedBySku(String sku, SearchQuery searchQuery);
}
