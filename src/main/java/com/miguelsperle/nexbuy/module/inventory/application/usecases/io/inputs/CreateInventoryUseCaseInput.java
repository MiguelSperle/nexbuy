package com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs;

public record CreateInventoryUseCaseInput(
        String sku
) {
    public static CreateInventoryUseCaseInput with(String sku) {
        return new CreateInventoryUseCaseInput(sku);
    }
}
