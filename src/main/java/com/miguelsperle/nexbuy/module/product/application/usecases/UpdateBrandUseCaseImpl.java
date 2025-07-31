package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.UpdateBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.domain.exceptions.BrandAlreadyExistsException;
import com.miguelsperle.nexbuy.module.product.domain.exceptions.BrandNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.ports.in.UpdateBrandUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.BrandRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;

public class UpdateBrandUseCaseImpl implements UpdateBrandUseCase {
    private final BrandRepository brandRepository;

    public UpdateBrandUseCaseImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public void execute(UpdateBrandUseCaseInput updateBrandUseCaseInput) {
        final Brand brand = this.getBrandById(updateBrandUseCaseInput.brandId());

        if (!updateBrandUseCaseInput.name().equalsIgnoreCase(brand.getName())) {
            if (this.verifyBrandAlreadyExistsByName(updateBrandUseCaseInput.name())) {
                throw BrandAlreadyExistsException.with("Brand with this name already exists");
            }
        }

        final Brand updatedBrand = brand.withName(updateBrandUseCaseInput.name());

        this.saveBrand(updatedBrand);
    }

    private Brand getBrandById(String brandId) {
        return this.brandRepository.findById(brandId)
                .orElseThrow(() -> BrandNotFoundException.with("Brand not found"));
    }

    private boolean verifyBrandAlreadyExistsByName(String name) {
        return this.brandRepository.existsByName(name);
    }

    private void saveBrand(Brand brand) {
        this.brandRepository.save(brand);
    }
}
