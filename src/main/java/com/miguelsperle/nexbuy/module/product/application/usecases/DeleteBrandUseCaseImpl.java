package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.DeleteBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.ports.in.DeleteBrandUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.BrandRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ProductRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

public class DeleteBrandUseCaseImpl implements DeleteBrandUseCase {
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;

    public DeleteBrandUseCaseImpl(BrandRepository brandRepository, ProductRepository productRepository) {
        this.brandRepository = brandRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void execute(DeleteBrandUseCaseInput deleteBrandUseCaseInput) {
        final Brand brand = this.getBrandById(deleteBrandUseCaseInput.brandId());

        if (this.verifyProductAlreadyExistsByBrandId(brand.getId())) {
            throw DomainException.with("Brand cannot be deleted because it is already associated with products", 409);
        }

        this.deleteBrandById(brand.getId());
    }

    private Brand getBrandById(String brandId) {
        return this.brandRepository.findById(brandId)
                .orElseThrow(() -> NotFoundException.with("Brand not found"));
    }

    private boolean verifyProductAlreadyExistsByBrandId(String brandId) {
        return this.productRepository.existsByBrandId(brandId);
    }

    private void deleteBrandById(String brandId) {
        this.productRepository.deleteById(brandId);
    }
}
