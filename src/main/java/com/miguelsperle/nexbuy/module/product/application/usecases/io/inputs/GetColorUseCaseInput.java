package com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs;

public record GetColorUseCaseInput(
        String colorId
) {
    public static GetColorUseCaseInput with(String colorId) {
        return new GetColorUseCaseInput(colorId);
    }
}
