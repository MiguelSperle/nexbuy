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
        final List<Category> rootCategories = this.categoryGateway.findAllByParentCategoryIdIsNull();

        final List<Category> subCategories = this.categoryGateway.findAllByParentCategoryIdIsNotNull();

        return new GetCategoriesUseCaseOutput(rootCategories, subCategories);
    }
}
