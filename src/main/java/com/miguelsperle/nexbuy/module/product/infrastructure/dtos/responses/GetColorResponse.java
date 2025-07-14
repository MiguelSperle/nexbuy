package com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetColorUseCaseOutput;

public record GetColorResponse(
        String id,
        String name
) {
    public static GetColorResponse fromOutput(GetColorUseCaseOutput getColorUseCaseOutput) {
        return new GetColorResponse(
                getColorUseCaseOutput.color().getId(),
                getColorUseCaseOutput.color().getName()
        );
    }
}
