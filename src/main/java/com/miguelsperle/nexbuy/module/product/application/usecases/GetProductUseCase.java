package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.exceptions.ProductNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IGetProductUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetProductUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetProductUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IProductGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Product;

public class GetProductUseCase implements IGetProductUseCase {
    private final IProductGateway productGateway;

    public GetProductUseCase(IProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    @Override
    public GetProductUseCaseOutput execute(GetProductUseCaseInput getProductUseCaseInput) {
        final Product product = this.getProductById(getProductUseCaseInput.productId());

        return GetProductUseCaseOutput.from(product);
    }

    private Product getProductById(String productId) {
        return this.productGateway.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }
}
