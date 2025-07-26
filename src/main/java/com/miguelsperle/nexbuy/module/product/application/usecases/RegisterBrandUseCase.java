package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.RegisterBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.domain.exceptions.BrandAlreadyExistsException;
import com.miguelsperle.nexbuy.module.product.application.ports.in.IRegisterBrandUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.IBrandRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;

public class RegisterBrandUseCase implements IRegisterBrandUseCase {
    private final IBrandRepository brandRepository;

    public RegisterBrandUseCase(IBrandRepository brandRepository) {
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
