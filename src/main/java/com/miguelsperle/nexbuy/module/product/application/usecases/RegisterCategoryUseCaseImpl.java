package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.RegisterCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.domain.exceptions.CategoryAlreadyExistsException;
import com.miguelsperle.nexbuy.module.product.application.ports.in.RegisterCategoryUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.CategoryRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;

public class RegisterCategoryUseCaseImpl implements RegisterCategoryUseCase {
    private final CategoryRepository categoryRepository;

    public RegisterCategoryUseCaseImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
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
        return this.categoryRepository.existsByName(name);
    }

    private void saveCategory(Category category) {
        this.categoryRepository.save(category);
    }
}
