package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.core.application.utils.SlugUtils;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.RegisterSubCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.exceptions.CategoryAlreadyExistsException;
import com.miguelsperle.nexbuy.module.product.application.exceptions.CategoryHierarchyLevelExceededException;
import com.miguelsperle.nexbuy.module.product.application.exceptions.CategoryNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IRegisterSubCategoryUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.ICategoryGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegisterSubCategoryUseCase implements IRegisterSubCategoryUseCase {
    private final ICategoryGateway categoryGateway;

    private final static int MAX_HIERARCHY_LEVEL = 2;

    @Override
    public void execute(RegisterSubCategoryUseCaseInput registerSubCategoryUseCaseInput) {
        if (this.verifyCategoryAlreadyExistsByName(registerSubCategoryUseCaseInput.getName())) {
            throw new CategoryAlreadyExistsException("Category already exists");
        }

        final Category parentCategory = this.getParentCategoryById(registerSubCategoryUseCaseInput.getParentCategoryId());

        final int hierarchyLevel = parentCategory.getHierarchyLevel() + 1;

        if (hierarchyLevel > MAX_HIERARCHY_LEVEL) {
            throw new CategoryHierarchyLevelExceededException("Category hierarchy level exceeded the maximum allowed limit");
        }

        final String slug = SlugUtils.createSlug(registerSubCategoryUseCaseInput.getName());

        final Category newCategory = Category.newCategory(registerSubCategoryUseCaseInput.getName(), registerSubCategoryUseCaseInput.getDescription(), slug, parentCategory, hierarchyLevel);

        this.categoryGateway.save(newCategory);
    }

    private boolean verifyCategoryAlreadyExistsByName(String name) {
        return this.categoryGateway.existsByName(name);
    }

    private Category getParentCategoryById(String parentCategoryId) {
        return this.categoryGateway.findById(parentCategoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Parent category not found"));
    }
}
