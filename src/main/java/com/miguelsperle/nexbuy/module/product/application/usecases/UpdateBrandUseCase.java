package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.UpdateBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.exceptions.BrandAlreadyExistsException;
import com.miguelsperle.nexbuy.module.product.application.exceptions.BrandNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IUpdateBrandUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IBrandGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;
import com.miguelsperle.nexbuy.module.product.domain.enums.BrandStatus;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class UpdateBrandUseCase implements IUpdateBrandUseCase {
    private final IBrandGateway brandGateway;

    @Override
    public void execute(UpdateBrandUseCaseInput updateBrandUseCaseInput) {
        final Brand brand = this.getBrandById(updateBrandUseCaseInput.getBrandId());

        if (!updateBrandUseCaseInput.getName().equalsIgnoreCase(brand.getName())) {
            if (this.verifyBrandAlreadyExistsByName(updateBrandUseCaseInput.getName())) {
                throw new BrandAlreadyExistsException("Brand already exists");
            }
        }

        final BrandStatus convertedToBrandStatus = BrandStatus.valueOf(updateBrandUseCaseInput.getStatus());

        final Brand brandUpdated = brand.withName(updateBrandUseCaseInput.getName()).withBrandStatus(convertedToBrandStatus);

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
