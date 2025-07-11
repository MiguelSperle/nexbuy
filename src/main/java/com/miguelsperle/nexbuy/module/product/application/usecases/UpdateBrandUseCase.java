package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.UpdateBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.exceptions.BrandAlreadyExistsException;
import com.miguelsperle.nexbuy.module.product.application.exceptions.BrandNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IUpdateBrandUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IBrandGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;

public class UpdateBrandUseCase implements IUpdateBrandUseCase {
    private final IBrandGateway brandGateway;

    public UpdateBrandUseCase(IBrandGateway brandGateway) {
        this.brandGateway = brandGateway;
    }

    @Override
    public void execute(UpdateBrandUseCaseInput updateBrandUseCaseInput) {
        final Brand brand = this.getBrandById(updateBrandUseCaseInput.brandId());

        if (!updateBrandUseCaseInput.name().equalsIgnoreCase(brand.getName())) {
            if (this.verifyBrandAlreadyExistsByName(updateBrandUseCaseInput.name())) {
                throw new BrandAlreadyExistsException("Brand with this name already exists");
            }
        }

        final Brand updatedBrand = brand.withName(updateBrandUseCaseInput.name());

        this.saveBrand(updatedBrand);
    }

    private Brand getBrandById(String brandId) {
        return this.brandGateway.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException("Brand not found"));
    }

    private boolean verifyBrandAlreadyExistsByName(String name) {
        return this.brandGateway.existsByName(name);
    }

    private void saveBrand(Brand brand) {
        this.brandGateway.save(brand);
    }
}
