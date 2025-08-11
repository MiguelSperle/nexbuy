package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetCategoriesUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetCategoriesUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.ports.in.GetCategoriesUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.CategoryRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;
import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.shared.domain.pagination.SearchQuery;

public class GetCategoriesUseCaseImpl implements GetCategoriesUseCase {
    private final CategoryRepository brandRepository;

    public GetCategoriesUseCaseImpl(CategoryRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public GetCategoriesUseCaseOutput execute(GetCategoriesUseCaseInput getCategoriesUseCaseInput) {
        final Pagination<Category> paginatedCategories = this.getAllPaginatedCategories(getCategoriesUseCaseInput.searchQuery());

        return GetCategoriesUseCaseOutput.from(paginatedCategories);
    }

    private Pagination<Category> getAllPaginatedCategories(SearchQuery searchQuery) {
        return this.brandRepository.findAllPaginated(searchQuery);
    }
}
