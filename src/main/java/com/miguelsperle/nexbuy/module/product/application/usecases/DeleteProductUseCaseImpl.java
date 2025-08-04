package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.core.application.ports.out.providers.DomainEventPublisherProvider;
import com.miguelsperle.nexbuy.core.domain.events.ProductDeletedEvent;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.DeleteProductUseCaseInput;
import com.miguelsperle.nexbuy.module.product.domain.exceptions.ProductNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.ports.in.DeleteProductUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ProductRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Product;
import com.miguelsperle.nexbuy.module.product.domain.enums.ProductStatus;

public class DeleteProductUseCaseImpl implements DeleteProductUseCase {
    private final ProductRepository productRepository;
    private final DomainEventPublisherProvider domainEventPublisherProvider;

    public DeleteProductUseCaseImpl(
            ProductRepository productRepository,
            DomainEventPublisherProvider domainEventPublisherProvider
    ) {
        this.productRepository = productRepository;
        this.domainEventPublisherProvider = domainEventPublisherProvider;
    }

    @Override
    public void execute(DeleteProductUseCaseInput deleteProductUseCaseInput) {
        final Product product = this.getProductById(deleteProductUseCaseInput.productId());

        final Product updatedProduct = product.withProductStatus(ProductStatus.DELETED);

        final Product savedProduct = this.saveProduct(updatedProduct);

        this.domainEventPublisherProvider.publishEvent(ProductDeletedEvent.from(
                savedProduct.getId()
        ));
    }

    private Product getProductById(String productId) {
        return this.productRepository.findById(productId)
                .orElseThrow(() -> ProductNotFoundException.with("Product not found"));
    }

    private Product saveProduct(Product product) {
        return this.productRepository.save(product);
    }
}
