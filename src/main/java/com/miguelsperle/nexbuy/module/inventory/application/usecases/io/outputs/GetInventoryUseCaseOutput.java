package com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs;

import com.miguelsperle.nexbuy.module.inventory.domain.entities.Inventory;

public record GetInventoryUseCaseOutput(
        Inventory inventory
) {
    public static GetInventoryUseCaseOutput from(Inventory inventory) {
        return new GetInventoryUseCaseOutput(inventory);
    }
}
