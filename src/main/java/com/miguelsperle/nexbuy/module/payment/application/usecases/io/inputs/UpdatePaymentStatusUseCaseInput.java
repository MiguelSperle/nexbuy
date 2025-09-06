package com.miguelsperle.nexbuy.module.payment.application.usecases.io.inputs;

import com.miguelsperle.nexbuy.module.payment.domain.enums.PaymentStatus;

public record UpdatePaymentStatusUseCaseInput(
    String paymentId,
    PaymentStatus paymentStatus
) {
    public static UpdatePaymentStatusUseCaseInput with(String paymentId, PaymentStatus paymentStatus) {
        return new UpdatePaymentStatusUseCaseInput(paymentId, paymentStatus);
    }
}
