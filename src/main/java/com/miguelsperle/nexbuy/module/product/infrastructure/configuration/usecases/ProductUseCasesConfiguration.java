package com.miguelsperle.nexbuy.module.product.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.core.application.ports.out.providers.DomainEventPublisherProvider;
import com.miguelsperle.nexbuy.core.application.ports.out.transaction.TransactionExecutor;
import com.miguelsperle.nexbuy.module.product.application.ports.in.*;
import com.miguelsperle.nexbuy.module.product.application.usecases.*;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.BrandRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.CategoryRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ColorRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ProductRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.providers.SkuProvider;
import com.miguelsperle.nexbuy.module.inventory.application.ports.in.CreateInventoryUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductUseCasesConfiguration {
    @Bean
    public RegisterProductUseCase registerProductUseCase(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            BrandRepository brandRepository,
            ColorRepository colorRepository,
            SkuProvider skuProvider,
            DomainEventPublisherProvider domainEventPublisherProvider
    ) {
        return new RegisterProductUseCaseImpl(
                productRepository,
                categoryRepository,
                brandRepository,
                colorRepository,
                skuProvider,
                domainEventPublisherProvider
        );
    }

    @Bean
    public UpdateProductUseCase updateProductUseCase(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            BrandRepository brandRepository,
            ColorRepository colorRepository,
            SkuProvider skuProvider
    ) {
        return new UpdateProductUseCaseImpl(
                productRepository,
                categoryRepository,
                brandRepository,
                colorRepository,
                skuProvider
        );
    }

    @Bean
    public UpdateProductStatusUseCase updateProductStatusUseCase(ProductRepository productRepository) {
        return new UpdateProductStatusUseCaseImpl(productRepository);
    }

    @Bean
    public DeleteProductUseCase deleteProductUseCase(ProductRepository productRepository) {
        return new DeleteProductUseCaseImpl(productRepository);
    }

    @Bean
    public GetProductsUseCase getProductsUseCase(ProductRepository productRepository) {
        return new GetProductsUseCaseImpl(productRepository);
    }

    @Bean
    public GetProductUseCase getProductUseCase(ProductRepository productRepository) {
        return new GetProductUseCaseImpl(productRepository);
    }
}
