package com.miguelsperle.nexbuy.module.product.application.dtos.outputs;

import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;

public record GetBrandUseCaseOutput(
        Brand brand
) {
    public static GetBrandUseCaseOutput from(Brand brand) {
        return new GetBrandUseCaseOutput(brand);
    }
}
