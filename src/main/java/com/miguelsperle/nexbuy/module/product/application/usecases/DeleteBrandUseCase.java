package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.DeleteBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.domain.exceptions.BrandAssociatedWithProductsException;
import com.miguelsperle.nexbuy.module.product.domain.exceptions.BrandNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.ports.in.IDeleteBrandUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.IBrandRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.IProductRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;

public class DeleteBrandUseCase implements IDeleteBrandUseCase {
    private final IBrandRepository brandRepository;
    private final IProductRepository productRepository;

    public DeleteBrandUseCase(IBrandRepository brandRepository, IProductRepository productRepository) {
        this.brandRepository = brandRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void execute(DeleteBrandUseCaseInput deleteBrandUseCaseInput) {
        final Brand brand = this.getBrandById(deleteBrandUseCaseInput.brandId());

        if (this.verifyProductAlreadyExistsByBrandId(brand.getId())) {
            throw BrandAssociatedWithProductsException.with("Brand cannot be deleted because it is already associated with products");
        }

        this.deleteBrandById(brand.getId());
    }

    private Brand getBrandById(String brandId) {
        return this.brandRepository.findById(brandId)
                .orElseThrow(() -> BrandNotFoundException.with("Brand not found"));
    }

    private boolean verifyProductAlreadyExistsByBrandId(String brandId) {
        return this.productRepository.existsByBrandId(brandId);
    }

    private void deleteBrandById(String brandId) {
        this.productRepository.deleteById(brandId);
    }
}
