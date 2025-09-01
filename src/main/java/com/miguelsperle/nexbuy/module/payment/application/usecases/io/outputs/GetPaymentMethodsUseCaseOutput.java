package com.miguelsperle.nexbuy.module.payment.application.usecases.io.outputs;

import com.miguelsperle.nexbuy.module.payment.domain.entities.PaymentMethod;

import java.util.List;

public record GetPaymentMethodsUseCaseOutput(
        List<PaymentMethod> paymentMethods
) {
    public static GetPaymentMethodsUseCaseOutput from(List<PaymentMethod> paymentMethods) {
        return new GetPaymentMethodsUseCaseOutput(paymentMethods);
    }
}
