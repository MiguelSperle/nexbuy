package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.core.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.core.domain.pagination.SearchQuery;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IGetActiveProductsUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetActiveProductsUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetActiveProductsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IProductGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Product;

public class GetActiveProductsUseCase implements IGetActiveProductsUseCase {
    private final IProductGateway productGateway;

    public GetActiveProductsUseCase(IProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    @Override
    public GetActiveProductsUseCaseOutput execute(GetActiveProductsUseCaseInput getActiveProductsUseCaseInput) {
        final Pagination<Product> paginatedProducts = this.getAllActivePaginatedProducts(getActiveProductsUseCaseInput.searchQuery());

        return GetActiveProductsUseCaseOutput.from(paginatedProducts);
    }

    private Pagination<Product> getAllActivePaginatedProducts(SearchQuery searchQuery) {
        return this.productGateway.findAllActivePaginated(searchQuery);
    }
}
