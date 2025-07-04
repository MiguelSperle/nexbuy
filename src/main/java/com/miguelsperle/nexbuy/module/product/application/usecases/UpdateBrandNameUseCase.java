package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.UpdateBrandNameUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.exceptions.BrandAlreadyExistsException;
import com.miguelsperle.nexbuy.module.product.application.exceptions.BrandNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IUpdateBrandNameUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IBrandGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateBrandNameUseCase implements IUpdateBrandNameUseCase {
    private final IBrandGateway brandGateway;

    @Override
    public void execute(UpdateBrandNameUseCaseInput updateBrandNameUseCaseInput) {
        final Brand brand = this.getBrandById(updateBrandNameUseCaseInput.getBrandId());

        if (!updateBrandNameUseCaseInput.getName().equalsIgnoreCase(brand.getName())) {
            if (this.verifyBrandAlreadyExistsByName(updateBrandNameUseCaseInput.getName())) {
                throw new BrandAlreadyExistsException("Brand already exists");
            }
        }

        final Brand brandUpdated = brand.withName(updateBrandNameUseCaseInput.getName());

        this.brandGateway.save(brandUpdated);
    }

    private Brand getBrandById(String brandId) {
        return this.brandGateway.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException("Brand not found"));
    }

    private boolean verifyBrandAlreadyExistsByName(String name) {
        return this.brandGateway.existsByName(name);
    }
}
