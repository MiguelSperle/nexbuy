package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetCategoryUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.ports.in.GetCategoryUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.CategoryRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

public class GetCategoryUseCaseImpl implements GetCategoryUseCase {
    private final CategoryRepository brandRepository;

    public GetCategoryUseCaseImpl(CategoryRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public GetCategoryUseCaseOutput execute(GetCategoryUseCaseInput getCategoryUseCaseInput) {
        final Category category = this.getCategoryById(getCategoryUseCaseInput.categoryId());

        return GetCategoryUseCaseOutput.from(category);
    }

    private Category getCategoryById(String categoryId) {
        return this.brandRepository.findById(categoryId)
                .orElseThrow(() -> NotFoundException.with("Category not found"));
    }
}
