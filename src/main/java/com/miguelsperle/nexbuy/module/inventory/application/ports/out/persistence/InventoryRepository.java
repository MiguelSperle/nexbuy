package com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence;

import com.miguelsperle.nexbuy.core.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.core.domain.pagination.SearchQuery;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.Inventory;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository {
    List<Inventory> findAll();
    Optional<Inventory> findById(String id);
    Inventory save(Inventory inventory);
    void deleteById(String id);
    Optional<Inventory> findBySku(String sku);
    Pagination<Inventory> findAllPaginated(SearchQuery searchQuery);
    Optional<Inventory> findByProductId(String productId);
}
