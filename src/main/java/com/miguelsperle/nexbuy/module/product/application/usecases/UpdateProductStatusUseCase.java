package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.UpdateProductStatusUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.exceptions.ProductAlreadyDeletedException;
import com.miguelsperle.nexbuy.module.product.application.exceptions.ProductNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.exceptions.ProductStatusNotAllowedException;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IUpdateProductStatusUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IProductGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Product;
import com.miguelsperle.nexbuy.module.product.domain.enums.ProductStatus;

public class UpdateProductStatusUseCase implements IUpdateProductStatusUseCase {
    private final IProductGateway productGateway;

    public UpdateProductStatusUseCase(IProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    @Override
    public void execute(UpdateProductStatusUseCaseInput updateProductStatusUseCaseInput) {
        final Product product = this.getProductById(updateProductStatusUseCaseInput.productId());

        final ProductStatus convertedToProductStatus = ProductStatus.valueOf(updateProductStatusUseCaseInput.productStatus());

        if (convertedToProductStatus == ProductStatus.DELETED) {
            throw new ProductStatusNotAllowedException("Status DELETED is not allowed to be set");
        }

        if (product.getProductStatus() == ProductStatus.DELETED) {
            throw new ProductAlreadyDeletedException("This product has already been deleted and cannot be updated");
        }

        final Product updatedProduct = product.withProductStatus(convertedToProductStatus);

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
