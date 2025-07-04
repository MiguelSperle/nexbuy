package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetCategoriesUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IGetCategoriesUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.ICategoryGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetCategoriesUseCase implements IGetCategoriesUseCase {
    private final ICategoryGateway categoryGateway;

    @Override
    public GetCategoriesUseCaseOutput execute() {
        final List<Category> rootCategories = this.getRootCategoriesByParentCategoryIdNull();

        final List<Category> subCategories = this.getSubCategoriesByParentCategoryIdNotNull();

        return new GetCategoriesUseCaseOutput(rootCategories, subCategories);
    }

    private List<Category> getRootCategoriesByParentCategoryIdNull() {
        return this.categoryGateway.findAllByParentCategoryIdNull();
    }

    private List<Category> getSubCategoriesByParentCategoryIdNotNull() {
        return this.categoryGateway.findAllByParentCategoryIdNotNull();
    }
}
