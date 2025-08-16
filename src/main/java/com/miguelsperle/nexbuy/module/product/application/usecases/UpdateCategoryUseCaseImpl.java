package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.UpdateCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.ports.in.usecases.UpdateCategoryUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.CategoryRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

public class UpdateCategoryUseCaseImpl implements UpdateCategoryUseCase {
    private final CategoryRepository categoryRepository;

    public UpdateCategoryUseCaseImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void execute(UpdateCategoryUseCaseInput updateCategoryUseCaseInput) {
        final Category category = this.getCategoryById(updateCategoryUseCaseInput.categoryId());

        if (!updateCategoryUseCaseInput.name().equalsIgnoreCase(category.getName())) {
            if (this.verifyCategoryAlreadyExistsByName(updateCategoryUseCaseInput.name())) {
                throw DomainException.with("Category with this name already exists", 409);
            }
        }

        final Category updatedCategory = category.withName(updateCategoryUseCaseInput.name());

        this.saveCategory(updatedCategory);
    }

    private Category getCategoryById(String id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> NotFoundException.with("Category not found"));
    }

    private boolean verifyCategoryAlreadyExistsByName(String name) {
        return this.categoryRepository.existsByName(name);
    }

    private void saveCategory(Category category) {
        this.categoryRepository.save(category);
    }
}
