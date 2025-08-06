package com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs;

import com.miguelsperle.nexbuy.module.inventory.domain.enums.InventoryMovementType;

public record CreateInventoryMovementUseCaseInput(
        String inventoryId,
        String sku,
        Integer quantity,
        InventoryMovementType movementType
) {
    public static CreateInventoryMovementUseCaseInput with(
            String inventoryId,
            String sku,
            Integer quantity,
            InventoryMovementType movementType
    ) {
        return new CreateInventoryMovementUseCaseInput(
                inventoryId,
                sku,
                quantity,
                movementType
        );
    }
}
