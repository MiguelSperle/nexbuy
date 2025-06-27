package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.RegisterProductCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.exceptions.ProductCategoryAlreadyExistsException;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IRegisterProductCategoryUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IProductCategoryGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.ProductCategory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegisterProductCategoryUseCase implements IRegisterProductCategoryUseCase {
    private final IProductCategoryGateway productCategoryGateway;

    @Override
    public void execute(RegisterProductCategoryUseCaseInput registerProductCategoryUseCaseInput) {
        if (this.verifyProductCategoryAlreadyExistsByName(registerProductCategoryUseCaseInput.getName())) {
            throw new ProductCategoryAlreadyExistsException("Product category already exists");
        }

        final ProductCategory newProductCategory = ProductCategory.newProductCategory(registerProductCategoryUseCaseInput.getName());

        this.productCategoryGateway.save(newProductCategory);
    }

    private boolean verifyProductCategoryAlreadyExistsByName(String name) {
        return this.productCategoryGateway.findByName(name).isPresent();
    }
}
