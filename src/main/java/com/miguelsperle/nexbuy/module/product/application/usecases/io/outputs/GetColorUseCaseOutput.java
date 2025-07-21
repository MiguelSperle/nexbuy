package com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs;

import com.miguelsperle.nexbuy.module.product.domain.entities.Color;

public record GetColorUseCaseOutput(
        Color color
) {
    public static GetColorUseCaseOutput from(Color color) {
        return new GetColorUseCaseOutput(color);
    }
}
