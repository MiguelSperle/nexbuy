package com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs;

import com.miguelsperle.nexbuy.module.product.domain.entities.Category;

public record GetCategoryUseCaseOutput(
        Category category
) {
    public static GetCategoryUseCaseOutput from(Category category) {
        return new GetCategoryUseCaseOutput(category);
    }
}
