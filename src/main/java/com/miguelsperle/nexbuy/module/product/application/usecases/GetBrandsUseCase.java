package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetBrandsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IGetBrandsUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IBrandGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;

import java.util.List;

public class GetBrandsUseCase implements IGetBrandsUseCase {
    private final IBrandGateway brandGateway;

    public GetBrandsUseCase(IBrandGateway brandGateway) {
        this.brandGateway = brandGateway;
    }

    @Override
    public GetBrandsUseCaseOutput execute() {
        final List<Brand> brands = this.getAllBrands();

        return new GetBrandsUseCaseOutput(brands);
    }

    private List<Brand> getAllBrands() {
        return this.brandGateway.findAll();
    }
}
