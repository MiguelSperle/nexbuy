package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetBrandsUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetBrandsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.ports.in.usecases.GetBrandsUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.BrandRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;
import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.shared.domain.pagination.SearchQuery;

public class GetBrandsUseCaseImpl implements GetBrandsUseCase {
    private final BrandRepository brandRepository;

    public GetBrandsUseCaseImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public GetBrandsUseCaseOutput execute(GetBrandsUseCaseInput getBrandsUseCaseInput) {
        final Pagination<Brand> paginatedBrands = this.getAllPaginatedBrands(getBrandsUseCaseInput.searchQuery());

        return GetBrandsUseCaseOutput.from(paginatedBrands);
    }

    private Pagination<Brand> getAllPaginatedBrands(SearchQuery searchQuery) {
        return this.brandRepository.findAllPaginated(searchQuery);
    }
}
