package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.RegisterRootCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.exceptions.CategoryAlreadyExistsException;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IRegisterRootCategoryUseCase;
import com.miguelsperle.nexbuy.core.application.utils.SlugUtils;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.ICategoryGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegisterRootCategoryUseCase implements IRegisterRootCategoryUseCase {
    private final ICategoryGateway categoryGateway;

    @Override
    public void execute(RegisterRootCategoryUseCaseInput registerRootCategoryUseCaseInput) {
        if (this.verifyCategoryAlreadyExistsByName(registerRootCategoryUseCaseInput.getName())) {
            throw new CategoryAlreadyExistsException("Category already exists");
        }

        final String slug = SlugUtils.createSlug(registerRootCategoryUseCaseInput.getName());

        final Category newCategory = Category.newCategory(registerRootCategoryUseCaseInput.getName(), registerRootCategoryUseCaseInput.getDescription(), slug, null, 0);

        this.categoryGateway.save(newCategory);
    }

    private boolean verifyCategoryAlreadyExistsByName(String name) {
        return this.categoryGateway.existsByName(name);
    }
}
