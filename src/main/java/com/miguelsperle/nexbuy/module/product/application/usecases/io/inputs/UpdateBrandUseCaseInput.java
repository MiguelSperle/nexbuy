package com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs;

public record UpdateBrandUseCaseInput(
        String brandId,
        String name
) {
    public static UpdateBrandUseCaseInput with(String brandId, String name) {
        return new UpdateBrandUseCaseInput(brandId, name);
    }
}

