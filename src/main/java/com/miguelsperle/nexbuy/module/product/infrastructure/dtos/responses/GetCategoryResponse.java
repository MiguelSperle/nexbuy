package com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetCategoryUseCaseOutput;

public record GetCategoryResponse(
        String id,
        String name
) {
    public static GetCategoryResponse from(GetCategoryUseCaseOutput getCategoryUseCaseOutput) {
        return new GetCategoryResponse(
                getCategoryUseCaseOutput.category().getId(),
                getCategoryUseCaseOutput.category().getName()
        );
    }
}
