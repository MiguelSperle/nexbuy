package com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.in.web.dtos.responses;

import com.miguelsperle.nexbuy.core.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs.GetInventoryMovementsUseCaseOutput;
import com.miguelsperle.nexbuy.module.inventory.domain.enums.InventoryMovementType;

import java.time.LocalDateTime;

public record GetInventoryMovementsResponse(
        String id,
        String sku,
        Integer quantity,
        InventoryMovementType movementType,
        LocalDateTime createdAt
) {
    public static Pagination<GetInventoryMovementsResponse> from(GetInventoryMovementsUseCaseOutput getInventoryMovementsUseCaseOutput) {
        return getInventoryMovementsUseCaseOutput.paginatedInventoryMovements().map(paginatedInventoryMovement ->
                new GetInventoryMovementsResponse(
                        paginatedInventoryMovement.getId(),
                        paginatedInventoryMovement.getSku(),
                        paginatedInventoryMovement.getQuantity(),
                        paginatedInventoryMovement.getMovementType(),
                        paginatedInventoryMovement.getCreatedAt()
                ));
    }
}
