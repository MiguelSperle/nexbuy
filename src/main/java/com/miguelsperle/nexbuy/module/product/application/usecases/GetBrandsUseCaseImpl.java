package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetBrandsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.ports.in.GetBrandsUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.BrandRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;

import java.util.List;

public class GetBrandsUseCaseImpl implements GetBrandsUseCase {
    private final BrandRepository brandRepository;

    public GetBrandsUseCaseImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public GetBrandsUseCaseOutput execute() {
        final List<Brand> brands = this.getAllBrands();

        return GetBrandsUseCaseOutput.from(brands);
    }

    private List<Brand> getAllBrands() {
        return this.brandRepository.findAll();
    }
}
