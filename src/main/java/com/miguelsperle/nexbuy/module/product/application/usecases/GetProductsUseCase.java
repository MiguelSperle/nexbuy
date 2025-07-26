package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.core.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.core.domain.pagination.SearchQuery;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetProductsUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetProductsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.ports.in.IGetProductsUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.IProductRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Product;

public class GetProductsUseCase implements IGetProductsUseCase {
    private final IProductRepository productRepository;

    public GetProductsUseCase(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public GetProductsUseCaseOutput execute(GetProductsUseCaseInput getProductsUseCaseInput) {
        final Pagination<Product> paginatedProducts = this.getAllPaginatedProducts(getProductsUseCaseInput.searchQuery());

        return GetProductsUseCaseOutput.from(paginatedProducts);
    }

    private Pagination<Product> getAllPaginatedProducts(SearchQuery searchQuery) {
        return this.productRepository.findAllPaginated(searchQuery);
    }
}
