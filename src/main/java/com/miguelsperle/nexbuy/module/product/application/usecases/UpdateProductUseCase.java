package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.UpdateProductUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.exceptions.ProductNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IUpdateProductUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IProductGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Product;

public class UpdateProductUseCase implements IUpdateProductUseCase {
    private final IProductGateway productGateway;

    public UpdateProductUseCase(IProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    @Override
    public void execute(UpdateProductUseCaseInput updateProductUseCaseInput) {
        final Product product = this.getProductById(updateProductUseCaseInput.productId());

        final Product updatedProduct = product.withDescription(updateProductUseCaseInput.description())
                .withPrice(updateProductUseCaseInput.price())
                .withWeight(updateProductUseCaseInput.weight())
                .withHeight(updateProductUseCaseInput.dimensionComplementInput().height())
                .withWidth(updateProductUseCaseInput.dimensionComplementInput().width())
                .withLength(updateProductUseCaseInput.dimensionComplementInput().length());

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
