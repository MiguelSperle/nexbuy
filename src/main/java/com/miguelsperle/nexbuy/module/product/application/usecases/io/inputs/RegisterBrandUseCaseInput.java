package com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs;

public record RegisterBrandUseCaseInput(
        String name
) {
    public static RegisterBrandUseCaseInput with(String name) {
        return new RegisterBrandUseCaseInput(name);
    }
}

