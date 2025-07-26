package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetCategoryUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.domain.exceptions.CategoryNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.ports.in.IGetCategoryUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ICategoryRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;

public class GetCategoryUseCase implements IGetCategoryUseCase {
    private final ICategoryRepository brandRepository;

    public GetCategoryUseCase(ICategoryRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public GetCategoryUseCaseOutput execute(GetCategoryUseCaseInput getCategoryUseCaseInput) {
        final Category category = this.getCategoryById(getCategoryUseCaseInput.categoryId());

        return GetCategoryUseCaseOutput.from(category);
    }

    private Category getCategoryById(String categoryId) {
        return this.brandRepository.findById(categoryId)
                .orElseThrow(() -> CategoryNotFoundException.with("Category not found"));
    }
}
