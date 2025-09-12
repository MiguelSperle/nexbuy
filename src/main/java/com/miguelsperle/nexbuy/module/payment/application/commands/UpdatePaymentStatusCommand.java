package com.miguelsperle.nexbuy.module.payment.application.commands;

import com.miguelsperle.nexbuy.module.payment.domain.enums.PaymentStatus;

public record UpdatePaymentStatusCommand(
        String paymentId,
        PaymentStatus paymentStatus
) {
    public static UpdatePaymentStatusCommand with(String paymentId, PaymentStatus paymentStatus) {
        return new UpdatePaymentStatusCommand(paymentId, paymentStatus);
    }
}
