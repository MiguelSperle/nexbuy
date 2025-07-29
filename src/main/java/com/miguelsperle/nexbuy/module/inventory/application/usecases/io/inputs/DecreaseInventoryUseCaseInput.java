package com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs;

public record DecreaseInventoryUseCaseInput(
        String sku,
        Integer quantity
) {
    public static DecreaseInventoryUseCaseInput with(String sku, Integer quantity) {
        return new DecreaseInventoryUseCaseInput(sku, quantity);
    }
}
