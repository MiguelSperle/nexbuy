package com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs;

import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;

import java.util.List;

public record GetBrandsUseCaseOutput(
        List<Brand> brands
) {
    public static GetBrandsUseCaseOutput from(List<Brand> brands) {
        return new GetBrandsUseCaseOutput(brands);
    }
}

