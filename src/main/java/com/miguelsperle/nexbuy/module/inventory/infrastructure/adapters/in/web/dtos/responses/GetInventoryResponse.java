package com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.in.web.dtos.responses;

import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs.GetInventoryUseCaseOutput;

public record GetInventoryResponse(
        String productId,
        String sku,
        Integer quantity
) {
    public static GetInventoryResponse from(GetInventoryUseCaseOutput getInventoryUseCaseOutput) {
        return new GetInventoryResponse(
                getInventoryUseCaseOutput.inventory().getProductId(),
                getInventoryUseCaseOutput.inventory().getSku(),
                getInventoryUseCaseOutput.inventory().getQuantity()
        );
    }
}
