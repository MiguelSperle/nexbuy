package com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetBrandsUseCaseOutput;

import java.util.List;

public record GetBrandsResponse(
        String id,
        String name
) {
    public static List<GetBrandsResponse> from(GetBrandsUseCaseOutput getBrandsUseCaseOutput) {
        return getBrandsUseCaseOutput.brands().stream().map(brand -> new GetBrandsResponse(
                brand.getId(),
                brand.getName()
        )).toList();
    }
}
