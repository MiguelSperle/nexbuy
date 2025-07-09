package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetCategoriesUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IGetCategoriesUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.ICategoryGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;

import java.util.List;

public class GetCategoriesUseCase implements IGetCategoriesUseCase {
    private final ICategoryGateway categoryGateway;

    public GetCategoriesUseCase(ICategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public GetCategoriesUseCaseOutput execute() {
        final List<Category> categories = this.getAllCategories();

        return new GetCategoriesUseCaseOutput(categories);
    }

    private List<Category> getAllCategories() {
        return this.categoryGateway.findAll();
    }
}
