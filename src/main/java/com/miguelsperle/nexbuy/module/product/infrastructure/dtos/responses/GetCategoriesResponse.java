package com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetCategoriesUseCaseOutput;

import java.util.List;

public record GetCategoriesResponse(
        String id,
        String name
) {
    public static List<GetCategoriesResponse> from(GetCategoriesUseCaseOutput getCategoriesUseCaseOutput) {
        return getCategoriesUseCaseOutput.categories().stream().map(category -> new GetCategoriesResponse(
                category.getId(),
                category.getName()
        )).toList();
    }
}
