package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.DeleteProductUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.exceptions.ProductNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IDeleteProductUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IProductGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Product;
import com.miguelsperle.nexbuy.module.product.domain.enums.ProductStatus;

public class DeleteProductUseCase implements IDeleteProductUseCase {
    private final IProductGateway productGateway;

    public DeleteProductUseCase(IProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    @Override
    public void execute(DeleteProductUseCaseInput deleteProductUseCaseInput) {
        final Product product = this.getProductById(deleteProductUseCaseInput.productId());

        final Product updatedProduct = product.withProductStatus(ProductStatus.DELETED);

        this.saveProduct(updatedProduct);
    }

    private Product getProductById(String productId) {
        return this.productGateway.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    private void saveProduct(Product product) {
        this.productGateway.save(product);
    }
}
