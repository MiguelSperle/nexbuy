package com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs;

import com.miguelsperle.nexbuy.core.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.InventoryMovement;

public record GetInventoryMovementsUseCaseOutput(
        Pagination<InventoryMovement> paginatedInventoryMovements
) {
    public static GetInventoryMovementsUseCaseOutput from(Pagination<InventoryMovement> paginatedInventoryMovements) {
        return new GetInventoryMovementsUseCaseOutput(paginatedInventoryMovements);
    }
}
