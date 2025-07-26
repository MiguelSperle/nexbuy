package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetCategoriesUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.ports.in.IGetCategoriesUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ICategoryRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;

import java.util.List;

public class GetCategoriesUseCase implements IGetCategoriesUseCase {
    private final ICategoryRepository brandRepository;

    public GetCategoriesUseCase(ICategoryRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public GetCategoriesUseCaseOutput execute() {
        final List<Category> categories = this.getAllCategories();

        return GetCategoriesUseCaseOutput.from(categories);
    }

    private List<Category> getAllCategories() {
        return this.brandRepository.findAll();
    }
}
