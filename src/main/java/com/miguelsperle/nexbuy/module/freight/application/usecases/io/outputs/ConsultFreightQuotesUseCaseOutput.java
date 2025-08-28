package com.miguelsperle.nexbuy.module.shipping.application.usecases.io.outputs;

public record ConsultFreightQuotesUseCaseOutput(
        String response
) {
    public static ConsultFreightQuotesUseCaseOutput from(String response) {
        return new ConsultFreightQuotesUseCaseOutput(response);
    }
}
