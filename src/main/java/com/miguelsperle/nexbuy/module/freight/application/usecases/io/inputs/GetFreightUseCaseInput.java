package com.miguelsperle.nexbuy.module.freight.application.usecases.io.inputs;

public record GetFreightUseCaseInput(
        String orderId
) {
    public static GetFreightUseCaseInput with(String orderId) {
        return new GetFreightUseCaseInput(orderId);
    }
}
