package com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs;

public record CreateInventoryUseCaseInput(
        String productId,
        String sku
) {
    public static CreateInventoryUseCaseInput with(String productId, String sku) {
        return new CreateInventoryUseCaseInput(productId, sku);
    }
}
