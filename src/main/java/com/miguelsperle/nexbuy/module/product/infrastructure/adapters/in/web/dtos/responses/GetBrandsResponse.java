package com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.dtos.responses;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetBrandsUseCaseOutput;
import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;

public record GetBrandsResponse(
        String id,
        String name
) {
    public static Pagination<GetBrandsResponse> from(GetBrandsUseCaseOutput getBrandsUseCaseOutput) {
        return getBrandsUseCaseOutput.paginatedBrands().map(paginatedBrand -> new GetBrandsResponse(
                paginatedBrand.getId(),
                paginatedBrand.getName()
        ));
    }
}
