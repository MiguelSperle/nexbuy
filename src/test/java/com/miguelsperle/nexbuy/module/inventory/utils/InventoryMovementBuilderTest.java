package com.miguelsperle.nexbuy.module.inventory.utils;

import com.miguelsperle.nexbuy.module.inventory.domain.entities.InventoryMovement;
import com.miguelsperle.nexbuy.module.inventory.domain.enums.InventoryMovementType;
import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

public class InventoryMovementBuilderTest {
    public static InventoryMovement create(String inventoryId, InventoryMovementType inventoryMovementType) {
        return InventoryMovement.with(
                IdentifierUtils.generateUUID(),
                inventoryId,
                "test-sku",
                1,
                inventoryMovementType,
                TimeUtils.now()
        );
    }
}
