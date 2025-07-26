package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.UpdateCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.domain.exceptions.CategoryAlreadyExistsException;
import com.miguelsperle.nexbuy.module.product.domain.exceptions.CategoryNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.ports.in.IUpdateCategoryUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ICategoryRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;

public class UpdateCategoryUseCase implements IUpdateCategoryUseCase {
    private final ICategoryRepository categoryRepository;

    public UpdateCategoryUseCase(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
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
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> CategoryNotFoundException.with("Category not found"));
    }

    private boolean verifyCategoryAlreadyExistsByName(String name) {
        return this.categoryRepository.existsByName(name);
    }

    private void saveCategory(Category category) {
        this.categoryRepository.save(category);
    }
}
