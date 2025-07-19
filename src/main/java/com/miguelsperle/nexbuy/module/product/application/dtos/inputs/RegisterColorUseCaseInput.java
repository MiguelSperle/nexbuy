package com.miguelsperle.nexbuy.module.product.application.dtos.inputs;

public record RegisterColorUseCaseInput(
        String name
) {
    public static RegisterColorUseCaseInput with(String name) {
        return new RegisterColorUseCaseInput(name);
    }
}
