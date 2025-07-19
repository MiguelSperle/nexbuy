package com.miguelsperle.nexbuy.module.product.application.dtos.inputs;

public record GetBrandUseCaseInput(
        String brandId
) {
    public static GetBrandUseCaseInput with(String brandId) {
        return new GetBrandUseCaseInput(brandId);
    }
}
