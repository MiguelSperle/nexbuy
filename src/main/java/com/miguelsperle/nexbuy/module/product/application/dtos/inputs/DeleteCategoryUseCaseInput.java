package com.miguelsperle.nexbuy.module.product.application.dtos.inputs;

public record DeleteCategoryUseCaseInput(
        String categoryId
) {
    public static DeleteCategoryUseCaseInput with(String categoryId) {
        return new DeleteCategoryUseCaseInput(categoryId);
    }
}
