package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.DeleteBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.exceptions.BrandAssociatedProductException;
import com.miguelsperle.nexbuy.module.product.application.exceptions.BrandNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IDeleteBrandUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IBrandGateway;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IProductGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteBrandUseCase implements IDeleteBrandUseCase {
    private final IBrandGateway brandGateway;
    private final IProductGateway productGateway;

    @Override
    public void execute(DeleteBrandUseCaseInput deleteBrandUseCaseInput) {
        final Brand brand = this.getBrandById(deleteBrandUseCaseInput.getBrandId());

        if (this.verifyExistsProductAssociatedWithBrandId(brand.getId())) {
            throw new BrandAssociatedProductException("Brand cannot be deleted because it has associated products");
        }

        this.brandGateway.deleteById(brand.getId());
    }

    private Brand getBrandById(String brandId) {
        return this.brandGateway.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException("Brand not found"));
    }

    private boolean verifyExistsProductAssociatedWithBrandId(String brandId) {
        return this.productGateway.existsByBrandId(brandId);
    }
}
