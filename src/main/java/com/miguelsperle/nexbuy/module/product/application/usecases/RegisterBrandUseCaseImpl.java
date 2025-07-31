package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.RegisterBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.domain.exceptions.BrandAlreadyExistsException;
import com.miguelsperle.nexbuy.module.product.application.ports.in.RegisterBrandUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.BrandRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;

public class RegisterBrandUseCaseImpl implements RegisterBrandUseCase {
    private final BrandRepository brandRepository;

    public RegisterBrandUseCaseImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public void execute(RegisterBrandUseCaseInput registerBrandUseCaseInput) {
        if (this.verifyBrandAlreadyExistsByName(registerBrandUseCaseInput.name())) {
            throw BrandAlreadyExistsException.with("Brand with this name already exists");
        }

        final Brand newBrand = Brand.newBrand(registerBrandUseCaseInput.name());

        this.saveBrand(newBrand);
    }

    private boolean verifyBrandAlreadyExistsByName(String name) {
        return this.brandRepository.existsByName(name);
    }

    private void saveBrand(Brand brand) {
        this.brandRepository.save(brand);
    }
}
