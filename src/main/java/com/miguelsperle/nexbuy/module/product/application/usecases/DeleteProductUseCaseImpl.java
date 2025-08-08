package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.DeleteProductUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.ports.in.DeleteProductUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ProductRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Product;
import com.miguelsperle.nexbuy.module.product.domain.enums.ProductStatus;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

public class DeleteProductUseCaseImpl implements DeleteProductUseCase {
    private final ProductRepository productRepository;

    public DeleteProductUseCaseImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void execute(DeleteProductUseCaseInput deleteProductUseCaseInput) {
        final Product product = this.getProductById(deleteProductUseCaseInput.productId());

        final Product updatedProduct = product.withProductStatus(ProductStatus.DELETED);

        this.saveProduct(updatedProduct);
    }

    private Product getProductById(String productId) {
        return this.productRepository.findById(productId)
                .orElseThrow(() -> NotFoundException.with("Product not found"));
    }

    private void saveProduct(Product product) {
        this.productRepository.save(product);
    }
}
