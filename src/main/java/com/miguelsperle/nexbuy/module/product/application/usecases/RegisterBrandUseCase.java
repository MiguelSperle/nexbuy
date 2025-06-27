package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.RegisterBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.exceptions.BrandAlreadyExistsException;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IRegisterBrandUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IBrandGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegisterBrandUseCase implements IRegisterBrandUseCase {
    private final IBrandGateway brandGateway;

    @Override
    public void execute(RegisterBrandUseCaseInput registerBrandUseCaseInput) {
        if (this.verifyBrandAlreadyExistsByName(registerBrandUseCaseInput.getName())) {
            throw new BrandAlreadyExistsException("Brand already exists");
        }

        final Brand newBrand = Brand.newBrand(registerBrandUseCaseInput.getName());

        this.brandGateway.save(newBrand);
    }

    private boolean verifyBrandAlreadyExistsByName(String name) {
        return this.brandGateway.findByName(name).isPresent();
    }
}
