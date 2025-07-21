package com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs;

public record GetCategoryUseCaseInput(
        String categoryId
) {
    public static GetCategoryUseCaseInput with(String categoryId) {
        return new GetCategoryUseCaseInput(categoryId);
    }
}
