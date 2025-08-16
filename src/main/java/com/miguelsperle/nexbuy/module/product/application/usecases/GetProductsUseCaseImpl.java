package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.shared.domain.pagination.SearchQuery;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetProductsUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetProductsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.ports.in.usecases.GetProductsUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ProductRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Product;

public class GetProductsUseCaseImpl implements GetProductsUseCase {
    private final ProductRepository productRepository;

    public GetProductsUseCaseImpl(ProductRepository productRepository) {
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
