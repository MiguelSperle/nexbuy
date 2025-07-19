package com.miguelsperle.nexbuy.module.product.application.dtos.inputs;

public record GetColorUseCaseInput(
        String colorId
) {
    public static GetColorUseCaseInput with(String colorId) {
        return new GetColorUseCaseInput(colorId);
    }
}
