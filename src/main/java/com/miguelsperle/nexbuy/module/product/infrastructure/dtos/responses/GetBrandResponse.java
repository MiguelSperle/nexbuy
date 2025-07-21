package com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetBrandUseCaseOutput;

public record GetBrandResponse(
        String id,
        String name
) {
    public static GetBrandResponse from(GetBrandUseCaseOutput getBrandUseCaseOutput) {
        return new GetBrandResponse(
                getBrandUseCaseOutput.brand().getId(),
                getBrandUseCaseOutput.brand().getName()
        );
    }
}

