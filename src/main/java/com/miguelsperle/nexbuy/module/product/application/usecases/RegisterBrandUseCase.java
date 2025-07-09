package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.RegisterBrandUseCaseInput;
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
            throw new BrandAlreadyExistsException("Brand with this name already exists");
        }

        final Brand newBrand = Brand.newBrand(registerBrandUseCaseInput.name(), registerBrandUseCaseInput.description());

        this.brandGateway.save(newBrand);
    }

    private boolean verifyBrandAlreadyExistsByName(String name) {
        return this.brandGateway.existsByName(name);
    }
}
