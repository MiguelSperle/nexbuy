package com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetBrandUseCaseOutput;

public record GetBrandResponse(
        String id,
        String name,
        String description
) {
    public static GetBrandResponse fromOutput(GetBrandUseCaseOutput getBrandUseCaseOutput) {
        return new GetBrandResponse(
                getBrandUseCaseOutput.brand().getId(),
                getBrandUseCaseOutput.brand().getName(),
                getBrandUseCaseOutput.brand().getDescription()
        );
    }
}

