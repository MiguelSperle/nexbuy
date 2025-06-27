package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetProductBrandsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IGetProductBrandsUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IProductBrandGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.ProductBrand;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetProductBrandsUseCase implements IGetProductBrandsUseCase {
    private final IProductBrandGateway productBrandGateway;

    @Override
    public GetProductBrandsUseCaseOutput execute() {
        final List<ProductBrand> productBrands = this.productBrandGateway.findAll();

        return new GetProductBrandsUseCaseOutput(productBrands);
    }
}
