package com.miguelsperle.nexbuy.module.payment.application.usecases.io.inputs;

public record UpdatePaymentMethodUseCaseInput(
        String paymentMethodId,
        String name
) {
    public static UpdatePaymentMethodUseCaseInput with(String paymentMethodId, String name) {
        return new UpdatePaymentMethodUseCaseInput(paymentMethodId, name);
    }
}
