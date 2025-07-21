package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.UpdateCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.exceptions.CategoryAlreadyExistsException;
import com.miguelsperle.nexbuy.module.product.application.exceptions.CategoryNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IUpdateCategoryUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.ICategoryGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;

public class UpdateCategoryUseCase implements IUpdateCategoryUseCase {
    private final ICategoryGateway categoryGateway;

    public UpdateCategoryUseCase(ICategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public void execute(UpdateCategoryUseCaseInput updateCategoryUseCaseInput) {
        final Category category = this.getCategoryById(updateCategoryUseCaseInput.categoryId());

        if (!updateCategoryUseCaseInput.name().equalsIgnoreCase(category.getName())) {
            if (this.verifyCategoryAlreadyExistsByName(updateCategoryUseCaseInput.name())) {
                throw CategoryAlreadyExistsException.with("Category with this name already exists");
            }
        }

        final Category updatedCategory = category.withName(updateCategoryUseCaseInput.name());

        this.saveCategory(updatedCategory);
    }

    private Category getCategoryById(String id) {
        return this.categoryGateway.findById(id)
                .orElseThrow(() -> CategoryNotFoundException.with("Category not found"));
    }

    private boolean verifyCategoryAlreadyExistsByName(String name) {
        return this.categoryGateway.existsByName(name);
    }

    private void saveCategory(Category category) {
        this.categoryGateway.save(category);
    }
}
