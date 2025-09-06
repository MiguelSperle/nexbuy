package com.miguelsperle.nexbuy.module.payment.application.usecases.io.outputs;

public record CreatePaymentUseCaseOutput(
        String sessionUrl
) {
    public static CreatePaymentUseCaseOutput from(String sessionUrl) {
        return new CreatePaymentUseCaseOutput(sessionUrl);
    }
}
