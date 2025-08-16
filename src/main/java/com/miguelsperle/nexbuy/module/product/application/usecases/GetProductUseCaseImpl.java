package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.ports.in.usecases.GetProductUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetProductUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetProductUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ProductRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Product;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

public class GetProductUseCaseImpl implements GetProductUseCase {
    private final ProductRepository productRepository;

    public GetProductUseCaseImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public GetProductUseCaseOutput execute(GetProductUseCaseInput getProductUseCaseInput) {
        final Product product = this.getProductById(getProductUseCaseInput.productId());

        return GetProductUseCaseOutput.from(product);
    }

    private Product getProductById(String productId) {
        return this.productRepository.findById(productId)
                .orElseThrow(() -> NotFoundException.with("Product not found"));
    }
}
