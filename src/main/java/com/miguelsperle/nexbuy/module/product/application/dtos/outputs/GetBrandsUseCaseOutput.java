package com.miguelsperle.nexbuy.module.product.application.dtos.outputs;

import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;

import java.util.List;

public record GetBrandsUseCaseOutput(
        List<Brand> brands
) {
}

