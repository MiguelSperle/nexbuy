package com.miguelsperle.nexbuy.module.payment.infrastructure.adapters.in.web.dtos.responses;

import com.miguelsperle.nexbuy.module.payment.application.usecases.io.outputs.GetPaymentUseCaseOutput;
import com.miguelsperle.nexbuy.module.payment.domain.enums.PaymentMethod;
import com.miguelsperle.nexbuy.module.payment.domain.enums.PaymentStatus;

public record GetPaymentResponse(
        PaymentMethod paymentMethod,
        PaymentStatus paymentStatus
) {
    public static GetPaymentResponse from(GetPaymentUseCaseOutput getPaymentUseCaseOutput) {
        return new GetPaymentResponse(
                getPaymentUseCaseOutput.payment().getPaymentMethod(),
                getPaymentUseCaseOutput.payment().getPaymentStatus()
        );
    }
}
