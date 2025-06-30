package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.RegisterCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.exceptions.CategoryAlreadyExistsException;
import com.miguelsperle.nexbuy.module.product.application.exceptions.CategoryNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IRegisterCategoryUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.ICategoryGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegisterCategoryUseCase implements IRegisterCategoryUseCase {
    private final ICategoryGateway categoryGateway;

    @Override
    public void execute(RegisterCategoryUseCaseInput registerCategoryUseCaseInput) {
        if (this.verifyCategoryAlreadyExistsByName(registerCategoryUseCaseInput.getName())) {
            throw new CategoryAlreadyExistsException("Category already exists");
        }

        Category parentCategory = null;

        if (registerCategoryUseCaseInput.getParentCategoryId() != null) {
            parentCategory = this.getParentCategoryById(registerCategoryUseCaseInput.getParentCategoryId());
        }

        final Category newCategory = Category.newCategory(registerCategoryUseCaseInput.getName(), registerCategoryUseCaseInput.getDescription(), parentCategory);

        this.categoryGateway.save(newCategory);
    }

    private boolean verifyCategoryAlreadyExistsByName(String name) {
        return this.categoryGateway.findByName(name).isPresent();
    }

    private Category getParentCategoryById(String id) {
        return this.categoryGateway.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Parent category not found"));
    }
}
