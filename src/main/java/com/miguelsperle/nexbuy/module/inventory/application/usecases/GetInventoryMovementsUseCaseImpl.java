package com.miguelsperle.nexbuy.module.inventory.application.usecases;

import com.miguelsperle.nexbuy.core.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.core.domain.pagination.SearchQuery;
import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.InventoryMovementRepository;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.GetInventoryMovementsUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs.GetInventoryMovementsUseCaseOutput;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.InventoryMovement;

public class GetInventoryMovementsUseCaseImpl implements com.miguelsperle.nexbuy.module.inventory.application.ports.in.GetInventoryMovementsUseCase {
    private final InventoryMovementRepository inventoryMovementRepository;

    public GetInventoryMovementsUseCaseImpl(InventoryMovementRepository inventoryMovementRepository) {
        this.inventoryMovementRepository = inventoryMovementRepository;
    }

    @Override
    public GetInventoryMovementsUseCaseOutput execute(GetInventoryMovementsUseCaseInput getInventoryMovementsUseCaseInput) {
        final Pagination<InventoryMovement> paginatedInventoryMovements = this.getAllPaginatedInventoryMovementsBySku(
                getInventoryMovementsUseCaseInput.sku(), getInventoryMovementsUseCaseInput.searchQuery()
        );

        return GetInventoryMovementsUseCaseOutput.from(paginatedInventoryMovements);
    }

    private Pagination<InventoryMovement> getAllPaginatedInventoryMovementsBySku(String sku, SearchQuery searchQuery) {
        return this.inventoryMovementRepository.findAllPaginatedBySku(sku, searchQuery);
    }
}
