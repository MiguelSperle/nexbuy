package com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs;

public record IncreaseInventoryUseCaseInput(
        String sku,
        Integer quantity
) {
    public static IncreaseInventoryUseCaseInput with(String sku, Integer quantity) {
        return new IncreaseInventoryUseCaseInput(sku, quantity);
    }
}
