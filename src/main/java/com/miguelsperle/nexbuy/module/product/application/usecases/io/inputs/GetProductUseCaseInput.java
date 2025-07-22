package com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs;

public record GetProductUseCaseInput(
        String productId
) {
    public static GetProductUseCaseInput with(String productId) {
        return new GetProductUseCaseInput(productId);
    }
}
