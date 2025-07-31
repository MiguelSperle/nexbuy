package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.UpdateProductStatusUseCaseInput;
import com.miguelsperle.nexbuy.module.product.domain.exceptions.ProductAlreadyDeletedException;
import com.miguelsperle.nexbuy.module.product.domain.exceptions.ProductNotFoundException;
import com.miguelsperle.nexbuy.module.product.domain.exceptions.ProductStatusNotAllowedException;
import com.miguelsperle.nexbuy.module.product.application.ports.in.UpdateProductStatusUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ProductRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Product;
import com.miguelsperle.nexbuy.module.product.domain.enums.ProductStatus;

public class UpdateProductStatusUseCaseImpl implements UpdateProductStatusUseCase {
    private final ProductRepository productRepository;

    public UpdateProductStatusUseCaseImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void execute(UpdateProductStatusUseCaseInput updateProductStatusUseCaseInput) {
        final Product product = this.getProductById(updateProductStatusUseCaseInput.productId());

        final ProductStatus convertedToProductStatus = ProductStatus.valueOf(updateProductStatusUseCaseInput.productStatus());

        if (convertedToProductStatus == ProductStatus.DELETED) {
            throw ProductStatusNotAllowedException.with("Status DELETED is not allowed to be set");
        }

        if (product.getProductStatus() == ProductStatus.DELETED) {
            throw ProductAlreadyDeletedException.with("This product has already been deleted and cannot be updated");
        }

        final Product updatedProduct = product.withProductStatus(convertedToProductStatus);

        this.saveProduct(updatedProduct);
    }

    private Product getProductById(String productId) {
        return this.productRepository.findById(productId)
                .orElseThrow(() -> ProductNotFoundException.with("Product not found"));
    }

    private void saveProduct(Product product) {
        this.productRepository.save(product);
    }
}
