package com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs;

public record RegisterCategoryUseCaseInput(
        String name
) {
    public static RegisterCategoryUseCaseInput with(String name) {
        return new RegisterCategoryUseCaseInput(name);
    }
}
