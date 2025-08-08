package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetBrandUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.ports.in.GetBrandUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.BrandRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

public class GetBrandUseCaseImpl implements GetBrandUseCase {
    private final BrandRepository brandRepository;

    public GetBrandUseCaseImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public GetBrandUseCaseOutput execute(GetBrandUseCaseInput getBrandUseCaseInput) {
        final Brand brand = this.getBrandById(getBrandUseCaseInput.brandId());

        return GetBrandUseCaseOutput.from(brand);
    }

    private Brand getBrandById(String brandId) {
        return this.brandRepository.findById(brandId)
                .orElseThrow(() -> NotFoundException.with("Brand not found"));
    }
}
