package com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs;

public record CheckInventoryAvailabilityUseCaseOutput(
        Boolean isAvailable
) {
    public static CheckInventoryAvailabilityUseCaseOutput from(Boolean isAvailable) {
        return new CheckInventoryAvailabilityUseCaseOutput(isAvailable);
    }
}
