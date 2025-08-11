package com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs;

import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;
import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;

public record GetBrandsUseCaseOutput(
        Pagination<Brand> paginatedBrands
) {
    public static GetBrandsUseCaseOutput from(Pagination<Brand> paginatedBrands) {
        return new GetBrandsUseCaseOutput(paginatedBrands);
    }
}

