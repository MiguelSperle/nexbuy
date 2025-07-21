package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetCategoryUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.exceptions.CategoryNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IGetCategoryUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.ICategoryGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;

public class GetCategoryUseCase implements IGetCategoryUseCase {
    private final ICategoryGateway categoryGateway;

    public GetCategoryUseCase(ICategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public GetCategoryUseCaseOutput execute(GetCategoryUseCaseInput getCategoryUseCaseInput) {
        final Category category = this.getCategoryById(getCategoryUseCaseInput.categoryId());

        return GetCategoryUseCaseOutput.from(category);
    }

    private Category getCategoryById(String categoryId) {
        return this.categoryGateway.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }
}
