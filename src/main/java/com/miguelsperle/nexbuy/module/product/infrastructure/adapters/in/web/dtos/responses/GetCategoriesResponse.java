package com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.dtos.responses;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetCategoriesUseCaseOutput;
import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;

public record GetCategoriesResponse(
        String id,
        String name
) {
    public static Pagination<GetCategoriesResponse> from(GetCategoriesUseCaseOutput getCategoriesUseCaseOutput) {
        return getCategoriesUseCaseOutput.paginatedCategories().map(paginatedCategory -> new GetCategoriesResponse(
                paginatedCategory.getId(),
                paginatedCategory.getName()
        ));
    }
}
