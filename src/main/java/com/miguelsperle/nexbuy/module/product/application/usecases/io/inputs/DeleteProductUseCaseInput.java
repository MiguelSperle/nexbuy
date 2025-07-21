package com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs;

public record DeleteProductUseCaseInput(
        String productId
) {
    public static DeleteProductUseCaseInput with(String productId) {
        return new DeleteProductUseCaseInput(productId);
    }
}
