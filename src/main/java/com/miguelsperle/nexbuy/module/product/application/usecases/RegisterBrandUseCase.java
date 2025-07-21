package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.RegisterBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.exceptions.BrandAlreadyExistsException;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IRegisterBrandUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IBrandGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;

public class RegisterBrandUseCase implements IRegisterBrandUseCase {
    private final IBrandGateway brandGateway;

    public RegisterBrandUseCase(IBrandGateway brandGateway) {
        this.brandGateway = brandGateway;
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
        return this.brandGateway.existsByName(name);
    }

    private void saveBrand(Brand brand) {
        this.brandGateway.save(brand);
    }
}
