package com.miguelsperle.nexbuy.module.inventory.application.usecases;

import com.miguelsperle.nexbuy.core.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.core.domain.pagination.SearchQuery;
import com.miguelsperle.nexbuy.module.inventory.application.ports.in.GetInventoryMovementsUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.InventoryMovementRepository;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.GetInventoryMovementsUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs.GetInventoryMovementsUseCaseOutput;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.InventoryMovement;

public class GetInventoryMovementsUseCaseImpl implements GetInventoryMovementsUseCase {
    private final InventoryMovementRepository inventoryMovementRepository;

    public GetInventoryMovementsUseCaseImpl(InventoryMovementRepository inventoryMovementRepository) {
        this.inventoryMovementRepository = inventoryMovementRepository;
    }

    @Override
    public GetInventoryMovementsUseCaseOutput execute(GetInventoryMovementsUseCaseInput getInventoryMovementsUseCaseInput) {
        final Pagination<InventoryMovement> paginatedInventoryMovements = this.getAllPaginatedInventoryMovements(
                getInventoryMovementsUseCaseInput.searchQuery()
        );

        return GetInventoryMovementsUseCaseOutput.from(paginatedInventoryMovements);
    }

    private Pagination<InventoryMovement> getAllPaginatedInventoryMovements(SearchQuery searchQuery) {
        return this.inventoryMovementRepository.findAllPaginated(searchQuery);
    }
}
