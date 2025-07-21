package com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs;

public record DeleteCategoryUseCaseInput(
        String categoryId
) {
    public static DeleteCategoryUseCaseInput with(String categoryId) {
        return new DeleteCategoryUseCaseInput(categoryId);
    }
}
