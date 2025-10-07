package com.miguelsperle.nexbuy.module.inventory.utils;

import com.miguelsperle.nexbuy.module.inventory.domain.entities.Inventory;
import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

public class InventoryBuilderTest {
    public static Inventory create() {
        return Inventory.with(
                IdentifierUtils.generateUUID(),
                IdentifierUtils.generateUUID(),
                "test-sku",
                1,
                TimeUtils.now()
        );
    }
}
