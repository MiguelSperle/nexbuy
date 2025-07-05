package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.core.application.utils.SlugUtils;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.UpdateCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.exceptions.CategoryAlreadyExistsException;
import com.miguelsperle.nexbuy.module.product.application.exceptions.CategoryNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IUpdateCategoryUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.ICategoryGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateCategoryUseCase implements IUpdateCategoryUseCase {
    private final ICategoryGateway categoryGateway;

    @Override
    public void execute(UpdateCategoryUseCaseInput updateCategoryUseCaseInput) {
        final Category category = this.getCategoryById(updateCategoryUseCaseInput.getCategoryId());

        if (!updateCategoryUseCaseInput.getName().equalsIgnoreCase(category.getName())) {
            if (this.verifyCategoryAlreadyExistsByName(updateCategoryUseCaseInput.getName())) {
                throw new CategoryAlreadyExistsException("Category already exists");
            }
        }

        final String slug = SlugUtils.createSlug(updateCategoryUseCaseInput.getName());

        final Category updatedCategory = category.withName(updateCategoryUseCaseInput.getName())
                .withDescription(updateCategoryUseCaseInput.getDescription())
                .withSlug(slug);

        this.categoryGateway.save(updatedCategory);
    }

    private Category getCategoryById(String categoryId) {
        return this.categoryGateway.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }

    private boolean verifyCategoryAlreadyExistsByName(String name) {
        return this.categoryGateway.existsByName(name);
    }
}
