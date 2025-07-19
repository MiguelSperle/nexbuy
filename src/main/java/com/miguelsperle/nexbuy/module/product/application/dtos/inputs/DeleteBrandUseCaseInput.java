package com.miguelsperle.nexbuy.module.product.application.dtos.inputs;

public record DeleteBrandUseCaseInput(
        String brandId
) {
    public static DeleteBrandUseCaseInput with(String brandId) {
        return new DeleteBrandUseCaseInput(brandId);
    }
}

