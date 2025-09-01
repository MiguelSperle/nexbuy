package com.miguelsperle.nexbuy.module.payments.application.usecases.io.inputs;

public record CreatePaymentMethodUseCaseInput(
        String name
) {
    public static CreatePaymentMethodUseCaseInput with(String name) {
        return new CreatePaymentMethodUseCaseInput(name);
    }
}
