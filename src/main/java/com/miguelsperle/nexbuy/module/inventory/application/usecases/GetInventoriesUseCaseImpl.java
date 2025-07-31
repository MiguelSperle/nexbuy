package com.miguelsperle.nexbuy.module.inventory.application.usecases;

import com.miguelsperle.nexbuy.core.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.core.domain.pagination.SearchQuery;
import com.miguelsperle.nexbuy.module.inventory.application.ports.in.GetInventoriesUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.InventoryRepository;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.GetInventoriesUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs.GetInventoriesUseCaseOutput;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.Inventory;

public class GetInventoriesUseCaseImpl implements GetInventoriesUseCase {
    private final InventoryRepository inventoryRepository;

    public GetInventoriesUseCaseImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public GetInventoriesUseCaseOutput execute(GetInventoriesUseCaseInput getInventoriesUseCaseInput) {
        final Pagination<Inventory> paginatedInventories = this.getAllPaginatedInventories(getInventoriesUseCaseInput.searchQuery());

        return GetInventoriesUseCaseOutput.from(paginatedInventories);
    }

    private Pagination<Inventory> getAllPaginatedInventories(SearchQuery searchQuery) {
        return this.inventoryRepository.findAllPaginated(searchQuery);
    }
}
