package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetBrandsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.ports.in.IGetBrandsUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.IBrandRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;

import java.util.List;

public class GetBrandsUseCase implements IGetBrandsUseCase {
    private final IBrandRepository brandRepository;

    public GetBrandsUseCase(IBrandRepository brandRepository) {
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
