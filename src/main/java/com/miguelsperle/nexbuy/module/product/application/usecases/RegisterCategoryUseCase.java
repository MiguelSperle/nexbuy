package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.RegisterCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.exceptions.CategoryAlreadyExistsException;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IRegisterCategoryUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.ICategoryGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;

public class RegisterCategoryUseCase implements IRegisterCategoryUseCase {
    private final ICategoryGateway categoryGateway;

    public RegisterCategoryUseCase(ICategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public void execute(RegisterCategoryUseCaseInput registerCategoryUseCaseInput) {
        if (this.verifyCategoryAlreadyExistsByName(registerCategoryUseCaseInput.name())) {
            throw new CategoryAlreadyExistsException("Category with this name already exists");
        }

        final Category newCategory = Category.newCategory(registerCategoryUseCaseInput.name(), registerCategoryUseCaseInput.description());

        this.categoryGateway.save(newCategory);
    }

    private boolean verifyCategoryAlreadyExistsByName(String name) {
        return this.categoryGateway.existsByName(name);
    }
}
