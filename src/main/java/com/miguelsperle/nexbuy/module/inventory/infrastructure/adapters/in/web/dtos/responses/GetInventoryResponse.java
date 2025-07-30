package com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.in.web.dtos.responses;

import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs.GetInventoryUseCaseOutput;

public record GetInventoryResponse(
        Integer quantity
) {
    public static GetInventoryResponse from(GetInventoryUseCaseOutput getInventoryUseCaseOutput) {
        return new GetInventoryResponse(getInventoryUseCaseOutput.inventory().getQuantity());
    }
}
