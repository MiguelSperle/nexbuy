package com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.dtos.responses;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetColorUseCaseOutput;

public record GetColorResponse(
        String id,
        String name
) {
    public static GetColorResponse from(GetColorUseCaseOutput getColorUseCaseOutput) {
        return new GetColorResponse(
                getColorUseCaseOutput.color().getId(),
                getColorUseCaseOutput.color().getName()
        );
    }
}
