package com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetCategoryUseCaseOutput;

public record GetCategoryResponse(
        String id,
        String name,
        String description
) {
    public static GetCategoryResponse fromOutput(GetCategoryUseCaseOutput getCategoryUseCaseOutput) {
        return new GetCategoryResponse(
                getCategoryUseCaseOutput.category().getId(),
                getCategoryUseCaseOutput.category().getName(),
                getCategoryUseCaseOutput.category().getDescription()
        );
    }
}
