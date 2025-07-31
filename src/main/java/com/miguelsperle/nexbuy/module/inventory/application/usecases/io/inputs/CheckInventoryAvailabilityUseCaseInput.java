package com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs;

public record CheckInventoryAvailabilityUseCaseInput(
        String sku
) {
    public static CheckInventoryAvailabilityUseCaseInput with(String sku) {
        return new CheckInventoryAvailabilityUseCaseInput(sku);
    }
}
