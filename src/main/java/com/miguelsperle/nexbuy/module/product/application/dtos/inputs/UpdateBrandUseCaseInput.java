package com.miguelsperle.nexbuy.module.product.application.dtos.inputs;

public record UpdateBrandUseCaseInput(
        String brandId,
        String name
) {
    public static UpdateBrandUseCaseInput with(String brandId, String name) {
        return new UpdateBrandUseCaseInput(brandId, name);
    }
}

