package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.RegisterCategoryUseCaseInput;
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
            throw CategoryAlreadyExistsException.with("Category with this name already exists");
        }

        final Category newCategory = Category.newCategory(registerCategoryUseCaseInput.name());

        this.saveCategory(newCategory);
    }

    private boolean verifyCategoryAlreadyExistsByName(String name) {
        return this.categoryGateway.existsByName(name);
    }

    private void saveCategory(Category category) {
        this.categoryGateway.save(category);
    }
}
