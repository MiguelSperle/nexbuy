package com.miguelsperle.nexbuy.module.payment.application.usecases.io.inputs;

public record CreatePaymentMethodUseCaseInput(
        String name
) {
    public static CreatePaymentMethodUseCaseInput with(String name) {
        return new CreatePaymentMethodUseCaseInput(name);
    }
}
