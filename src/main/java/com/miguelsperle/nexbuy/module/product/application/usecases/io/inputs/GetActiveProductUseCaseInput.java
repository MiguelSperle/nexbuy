package com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs;

public record GetActiveProductUseCaseInput(
        String productId
) {
    public static GetActiveProductUseCaseInput with(String productId) {
        return new GetActiveProductUseCaseInput(productId);
    }
}
