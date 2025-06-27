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
        final List<Category> categories = this.categoryGateway.findAll();

        return new GetCategoriesUseCaseOutput(categories);
    }
}
