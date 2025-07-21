package com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs;

public record GetBrandUseCaseInput(
        String brandId
) {
    public static GetBrandUseCaseInput with(String brandId) {
        return new GetBrandUseCaseInput(brandId);
    }
}
