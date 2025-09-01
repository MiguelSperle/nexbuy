package com.miguelsperle.nexbuy.module.payments.application.usecases.io.outputs;

import com.miguelsperle.nexbuy.module.payments.domain.entities.PaymentMethod;

import java.util.List;

public record GetPaymentMethodsUseCaseOutput(
        List<PaymentMethod> paymentMethods
) {
    public static GetPaymentMethodsUseCaseOutput from(List<PaymentMethod> paymentMethods) {
        return new GetPaymentMethodsUseCaseOutput(paymentMethods);
    }
}
