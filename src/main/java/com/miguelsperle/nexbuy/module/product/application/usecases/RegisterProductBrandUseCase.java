package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.RegisterProductBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.exceptions.ProductBrandAlreadyExistsException;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IRegisterProductBrandUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IProductBrandGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.ProductBrand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegisterProductBrandUseCase implements IRegisterProductBrandUseCase {
    private final IProductBrandGateway productBrandGateway;

    @Override
    public void execute(RegisterProductBrandUseCaseInput registerProductBrandUseCaseInput) {
        if (this.verifyProductBrandAlreadyExistsByName(registerProductBrandUseCaseInput.getName())) {
            throw new ProductBrandAlreadyExistsException("Product brand already exists");
        }

        final ProductBrand newProductbrand = ProductBrand.newProductBrand(registerProductBrandUseCaseInput.getName());

        this.productBrandGateway.save(newProductbrand);
    }

    private boolean verifyProductBrandAlreadyExistsByName(String name) {
        return this.productBrandGateway.findByName(name).isPresent();
    }
}
