package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetBrandsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IGetBrandsUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IBrandGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetBrandsUseCase implements IGetBrandsUseCase {
    private final IBrandGateway brandGateway;

    @Override
    public GetBrandsUseCaseOutput execute() {
        final List<Brand> brands = this.brandGateway.findAll();

        return new GetBrandsUseCaseOutput(brands);
    }
}
