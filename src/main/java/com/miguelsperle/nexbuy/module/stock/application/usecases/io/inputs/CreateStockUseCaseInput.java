package com.miguelsperle.nexbuy.module.stock.application.usecases.io.inputs;

public record CreateStockUseCaseInput(
        String productId,
        String sku,
        Integer quantity
) {
    public static CreateStockUseCaseInput with(
            String productId,
            String sku,
            Integer quantity
    ) {
        return new CreateStockUseCaseInput(
                productId,
                sku,
                quantity
        );
    }
}
