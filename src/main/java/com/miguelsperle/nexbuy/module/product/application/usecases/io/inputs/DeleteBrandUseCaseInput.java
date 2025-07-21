package com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs;

public record DeleteBrandUseCaseInput(
        String brandId
) {
    public static DeleteBrandUseCaseInput with(String brandId) {
        return new DeleteBrandUseCaseInput(brandId);
    }
}

