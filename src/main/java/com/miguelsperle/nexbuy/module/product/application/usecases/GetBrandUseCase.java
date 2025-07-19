package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.GetBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetBrandUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.exceptions.BrandNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IGetBrandUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IBrandGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;

public class GetBrandUseCase implements IGetBrandUseCase {
    private final IBrandGateway brandGateway;

    public GetBrandUseCase(IBrandGateway brandGateway) {
        this.brandGateway = brandGateway;
    }

    @Override
    public GetBrandUseCaseOutput execute(GetBrandUseCaseInput getBrandUseCaseInput) {
        final Brand brand = this.getBrandById(getBrandUseCaseInput.brandId());

        return GetBrandUseCaseOutput.from(brand);
    }

    private Brand getBrandById(String brandId) {
        return this.brandGateway.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException("Brand not found"));
    }
}
