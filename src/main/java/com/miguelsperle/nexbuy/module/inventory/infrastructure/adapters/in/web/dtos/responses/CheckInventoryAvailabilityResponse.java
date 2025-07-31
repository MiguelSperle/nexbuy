package com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.in.web.dtos.responses;

import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs.CheckInventoryAvailabilityUseCaseOutput;

public record CheckInventoryAvailabilityResponse(
        Boolean isAvailable
) {
    public static CheckInventoryAvailabilityResponse from(CheckInventoryAvailabilityUseCaseOutput checkInventoryAvailabilityUseCaseOutput) {
        return new CheckInventoryAvailabilityResponse(checkInventoryAvailabilityUseCaseOutput.isAvailable());
    }
}
