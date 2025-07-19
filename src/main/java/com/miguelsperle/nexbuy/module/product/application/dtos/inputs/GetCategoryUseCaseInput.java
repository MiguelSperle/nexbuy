package com.miguelsperle.nexbuy.module.product.application.dtos.inputs;

public record GetCategoryUseCaseInput(
        String categoryId
) {
    public static GetCategoryUseCaseInput with(String categoryId) {
        return new GetCategoryUseCaseInput(categoryId);
    }
}
