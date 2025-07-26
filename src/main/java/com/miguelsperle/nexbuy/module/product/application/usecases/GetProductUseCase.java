package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.domain.exceptions.ProductNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.ports.in.IGetProductUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetProductUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetProductUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.IProductRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Product;

public class GetProductUseCase implements IGetProductUseCase {
    private final IProductRepository productRepository;

    public GetProductUseCase(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public GetProductUseCaseOutput execute(GetProductUseCaseInput getProductUseCaseInput) {
        final Product product = this.getProductById(getProductUseCaseInput.productId());

        return GetProductUseCaseOutput.from(product);
    }

    private Product getProductById(String productId) {
        return this.productRepository.findById(productId)
                .orElseThrow(() -> ProductNotFoundException.with("Product not found"));
    }
}
