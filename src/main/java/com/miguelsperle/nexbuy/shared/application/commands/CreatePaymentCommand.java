package com.miguelsperle.nexbuy.shared.application.commands;

import com.miguelsperle.nexbuy.module.payment.domain.enums.PaymentMethod;

import java.math.BigDecimal;

public record CreatePaymentCommand(
        PaymentMethod paymentMethod,
        String paymentToken,
        BigDecimal totalAmount
) {
    public static CreatePaymentCommand with(
            PaymentMethod paymentMethod,
            String paymentToken,
            BigDecimal totalAmount
    ) {
        return new CreatePaymentCommand(
                paymentMethod,
                paymentToken,
                totalAmount
        );
    }
}
