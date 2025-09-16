package com.miguelsperle.nexbuy.module.payment.application.usecases.io.inputs;

public record GetPaymentUseCaseInput(
        String orderId
) {
    public static GetPaymentUseCaseInput with(String orderId) {
        return new GetPaymentUseCaseInput(orderId);
    }
}
