package com.miguelsperle.nexbuy.module.payment.infrastructure.adapters.in.web.dtos.responses;

import com.miguelsperle.nexbuy.module.payment.application.usecases.io.outputs.CreatePaymentUseCaseOutput;

public record CreatePaymentResponse(
        String sessionUrl
) {
    public static CreatePaymentResponse from(CreatePaymentUseCaseOutput createPaymentUseCaseOutput) {
        return new CreatePaymentResponse(createPaymentUseCaseOutput.sessionUrl());
    }
}
