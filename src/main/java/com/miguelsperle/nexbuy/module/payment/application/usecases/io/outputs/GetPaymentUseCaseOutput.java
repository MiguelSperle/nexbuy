package com.miguelsperle.nexbuy.module.payment.application.usecases.io.outputs;

import com.miguelsperle.nexbuy.module.payment.domain.entities.Payment;

public record GetPaymentUseCaseOutput(
        Payment payment
) {
    public static GetPaymentUseCaseOutput from(Payment payment) {
        return new GetPaymentUseCaseOutput(payment);
    }
}
