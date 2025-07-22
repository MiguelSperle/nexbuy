package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.exceptions.ProductNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IGetActiveProductUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetActiveProductUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetActiveProductUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IProductGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Product;

public class GetActiveProductUseCase implements IGetActiveProductUseCase {
    private final IProductGateway productGateway;

    public GetActiveProductUseCase(IProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    @Override
    public GetActiveProductUseCaseOutput execute(GetActiveProductUseCaseInput getActiveProductUseCaseInput) {
        final Product product = this.getActiveProductById(getActiveProductUseCaseInput.productId());

        return GetActiveProductUseCaseOutput.from(product);
    }

    private Product getActiveProductById(String productId) {
        return this.productGateway.findActiveById(productId)
                .orElseThrow(() -> ProductNotFoundException.with("Product not found"));
    }
}
