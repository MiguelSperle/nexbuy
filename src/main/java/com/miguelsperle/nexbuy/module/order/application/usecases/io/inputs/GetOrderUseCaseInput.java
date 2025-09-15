package com.miguelsperle.nexbuy.module.order.application.usecases.io.inputs;

public record GetOrderUseCaseInput(
        String orderId
) {
    public static GetOrderUseCaseInput with(String orderId) {
        return new GetOrderUseCaseInput(orderId);
    }
}
