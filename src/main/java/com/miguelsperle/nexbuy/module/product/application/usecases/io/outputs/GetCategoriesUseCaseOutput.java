package com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs;

import com.miguelsperle.nexbuy.module.product.domain.entities.Category;
import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;

public record GetCategoriesUseCaseOutput(
        Pagination<Category> paginatedCategories
) {
    public static GetCategoriesUseCaseOutput from(Pagination<Category> paginatedCategories) {
        return new GetCategoriesUseCaseOutput(paginatedCategories);
    }
}
