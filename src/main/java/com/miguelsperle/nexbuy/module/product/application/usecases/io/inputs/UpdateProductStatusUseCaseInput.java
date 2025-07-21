package com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs;

public record UpdateProductStatusUseCaseInput(
        String productId,
        String productStatus
) {
    public static UpdateProductStatusUseCaseInput with(String productId, String productStatus) {
        return new UpdateProductStatusUseCaseInput(productId, productStatus);
    }
}
