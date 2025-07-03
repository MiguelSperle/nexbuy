package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.UpdateBrandStatusUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.exceptions.BrandNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IUpdateBrandStatusUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IBrandGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;
import com.miguelsperle.nexbuy.module.product.domain.enums.BrandStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateBrandStatusUseCase implements IUpdateBrandStatusUseCase {
    private final IBrandGateway brandGateway;

    @Override
    public void execute(UpdateBrandStatusUseCaseInput updateBrandStatusUseCaseInput) {
        final Brand brand = this.getBrandById(updateBrandStatusUseCaseInput.getBrandId());

        final BrandStatus convertedToBrandStatus = BrandStatus.valueOf(updateBrandStatusUseCaseInput.getBrandStatus());

        final Brand brandUpdated = brand.withBrandStatus(convertedToBrandStatus);

        this.brandGateway.save(brandUpdated);
    }

    private Brand getBrandById(String brandId) {
        return this.brandGateway.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException("Brand not found"));
    }
}
