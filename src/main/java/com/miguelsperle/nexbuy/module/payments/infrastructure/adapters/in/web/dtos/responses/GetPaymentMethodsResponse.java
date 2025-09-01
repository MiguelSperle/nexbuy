package com.miguelsperle.nexbuy.module.payments.infrastructure.adapters.in.web.dtos.responses;

import com.miguelsperle.nexbuy.module.payments.application.usecases.io.outputs.GetPaymentMethodsUseCaseOutput;

import java.util.List;

public record GetPaymentMethodsResponse(
        String id,
        String name
) {
    public static List<GetPaymentMethodsResponse> from(GetPaymentMethodsUseCaseOutput getPaymentMethodsUseCaseOutput) {
        return getPaymentMethodsUseCaseOutput.paymentMethods().stream().map(paymentMethod -> new GetPaymentMethodsResponse(
                paymentMethod.getId(),
                paymentMethod.getName()
        )).toList();
    }
}
