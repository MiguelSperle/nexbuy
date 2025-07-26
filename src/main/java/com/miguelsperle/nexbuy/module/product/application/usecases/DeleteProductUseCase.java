package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.DeleteProductUseCaseInput;
import com.miguelsperle.nexbuy.module.product.domain.exceptions.ProductNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.ports.in.IDeleteProductUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.IProductRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Product;
import com.miguelsperle.nexbuy.module.product.domain.enums.ProductStatus;

public class DeleteProductUseCase implements IDeleteProductUseCase {
    private final IProductRepository productRepository;

    public DeleteProductUseCase(IProductRepository productRepository) {
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
                .orElseThrow(() -> ProductNotFoundException.with("Product not found"));
    }

    private void saveProduct(Product product) {
        this.productRepository.save(product);
    }
}
