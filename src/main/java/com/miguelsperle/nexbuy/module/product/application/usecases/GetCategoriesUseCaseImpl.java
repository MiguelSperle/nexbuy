package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetCategoriesUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.ports.in.GetCategoriesUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.CategoryRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;

import java.util.List;

public class GetCategoriesUseCaseImpl implements GetCategoriesUseCase {
    private final CategoryRepository brandRepository;

    public GetCategoriesUseCaseImpl(CategoryRepository brandRepository) {
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
