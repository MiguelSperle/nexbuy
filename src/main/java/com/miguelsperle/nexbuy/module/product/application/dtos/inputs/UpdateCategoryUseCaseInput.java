package com.miguelsperle.nexbuy.module.product.application.dtos.inputs;

public record UpdateCategoryUseCaseInput(
        String categoryId,
        String name
) {
    public static UpdateCategoryUseCaseInput with(String categoryId, String name) {
        return new UpdateCategoryUseCaseInput(categoryId, name);
    }
}
