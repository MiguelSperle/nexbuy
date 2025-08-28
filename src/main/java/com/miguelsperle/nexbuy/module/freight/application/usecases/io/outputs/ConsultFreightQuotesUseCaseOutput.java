package com.miguelsperle.nexbuy.module.freight.application.usecases.io.outputs;

public record ConsultFreightQuotesUseCaseOutput(
        String response
) {
    public static ConsultFreightQuotesUseCaseOutput from(String response) {
        return new ConsultFreightQuotesUseCaseOutput(response);
    }
}
