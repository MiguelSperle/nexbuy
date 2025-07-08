package com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetBrandsUseCaseOutput;

import java.util.List;

public record GetBrandsResponse(
        String id,
        String name
) {
    public static List<GetBrandsResponse> fromOutput(GetBrandsUseCaseOutput getBrandsUseCaseOutput) {
        return getBrandsUseCaseOutput.brands().stream().map(productBrand -> new GetBrandsResponse(
                productBrand.getId(),
                productBrand.getName()
        )).toList();
    }
}
