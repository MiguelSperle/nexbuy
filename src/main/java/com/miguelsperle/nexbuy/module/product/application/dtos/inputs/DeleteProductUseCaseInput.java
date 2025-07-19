package com.miguelsperle.nexbuy.module.product.application.dtos.inputs;

public record DeleteProductUseCaseInput(
        String productId
) {
    public static DeleteProductUseCaseInput with(String productId) {
        return new DeleteProductUseCaseInput(productId);
    }
}
