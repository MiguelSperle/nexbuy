package com.miguelsperle.nexbuy.module.product.application.dtos.inputs;

public record UpdateProductStatusUseCaseInput(
        String productId,
        String productStatus
) {
}
