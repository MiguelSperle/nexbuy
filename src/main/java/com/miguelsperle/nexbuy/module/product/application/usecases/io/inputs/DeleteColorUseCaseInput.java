package com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs;

public record DeleteColorUseCaseInput(
        String colorId
) {
    public static DeleteColorUseCaseInput with(String colorId) {
        return new DeleteColorUseCaseInput(colorId);
    }
}
