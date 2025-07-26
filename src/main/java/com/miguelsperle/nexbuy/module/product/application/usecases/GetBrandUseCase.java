package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetBrandUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.domain.exceptions.BrandNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.ports.in.IGetBrandUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.IBrandRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;

public class GetBrandUseCase implements IGetBrandUseCase {
    private final IBrandRepository brandRepository;

    public GetBrandUseCase(IBrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public GetBrandUseCaseOutput execute(GetBrandUseCaseInput getBrandUseCaseInput) {
        final Brand brand = this.getBrandById(getBrandUseCaseInput.brandId());

        return GetBrandUseCaseOutput.from(brand);
    }

    private Brand getBrandById(String brandId) {
        return this.brandRepository.findById(brandId)
                .orElseThrow(() -> BrandNotFoundException.with("Brand not found"));
    }
}
