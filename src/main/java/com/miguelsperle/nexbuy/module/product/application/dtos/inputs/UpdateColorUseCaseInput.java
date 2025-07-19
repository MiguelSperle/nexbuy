package com.miguelsperle.nexbuy.module.product.application.dtos.inputs;

public record UpdateColorUseCaseInput(
        String colorId,
        String name
) {
    public static UpdateColorUseCaseInput with(String colorId, String name) {
        return new UpdateColorUseCaseInput(colorId, name);
    }
}
