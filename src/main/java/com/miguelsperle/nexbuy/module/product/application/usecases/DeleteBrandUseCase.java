package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.DeleteBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.exceptions.BrandAssociatedWithProductsException;
import com.miguelsperle.nexbuy.module.product.application.exceptions.BrandNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IDeleteBrandUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IBrandGateway;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IProductGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;

public class DeleteBrandUseCase implements IDeleteBrandUseCase {
    private final IBrandGateway brandGateway;
    private final IProductGateway productGateway;

    public DeleteBrandUseCase(IBrandGateway brandGateway, IProductGateway productGateway) {
        this.brandGateway = brandGateway;
        this.productGateway = productGateway;
    }

    @Override
    public void execute(DeleteBrandUseCaseInput deleteBrandUseCaseInput) {
        final Brand brand = this.getBrandById(deleteBrandUseCaseInput.brandId());

        if (this.verifyProductAlreadyExistsByBrandId(brand.getId())) {
            throw new BrandAssociatedWithProductsException("Brand cannot be deleted because it is associated with products");
        }

        this.brandGateway.deleteById(brand.getId());
    }

    private Brand getBrandById(String brandId) {
        return this.brandGateway.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException("Brand not found"));
    }

    private boolean verifyProductAlreadyExistsByBrandId(String brandId) {
        return this.productGateway.existsByBrandId(brandId);
    }
}
