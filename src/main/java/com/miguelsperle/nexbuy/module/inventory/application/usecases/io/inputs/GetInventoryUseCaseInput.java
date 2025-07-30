package com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs;

public record GetInventoryUseCaseInput(
        String sku
) {
    public static GetInventoryUseCaseInput with(String sku) {
        return new GetInventoryUseCaseInput(sku);
    }
}
