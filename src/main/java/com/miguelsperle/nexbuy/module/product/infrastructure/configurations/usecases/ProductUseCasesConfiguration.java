package com.miguelsperle.nexbuy.module.product.infrastructure.configurations.usecases;

import com.miguelsperle.nexbuy.module.product.application.abstractions.usecases.*;
import com.miguelsperle.nexbuy.shared.application.abstractions.producer.MessageProducer;
import com.miguelsperle.nexbuy.module.product.application.usecases.*;
import com.miguelsperle.nexbuy.module.product.application.abstractions.repositories.BrandRepository;
import com.miguelsperle.nexbuy.module.product.application.abstractions.repositories.CategoryRepository;
import com.miguelsperle.nexbuy.module.product.application.abstractions.repositories.ColorRepository;
import com.miguelsperle.nexbuy.module.product.application.abstractions.repositories.ProductRepository;
import com.miguelsperle.nexbuy.module.product.application.abstractions.providers.SkuProvider;
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
            MessageProducer messageProducer
    ) {
        return new RegisterProductUseCaseImpl(
                productRepository,
                categoryRepository,
                brandRepository,
                colorRepository,
                skuProvider,
                messageProducer
        );
    }

    @Bean
    public UpdateProductUseCase updateProductUseCase(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            BrandRepository brandRepository,
            ColorRepository colorRepository,
            SkuProvider skuProvider,
            MessageProducer messageProducer
    ) {
        return new UpdateProductUseCaseImpl(
                productRepository,
                categoryRepository,
                brandRepository,
                colorRepository,
                skuProvider,
                messageProducer
        );
    }

    @Bean
    public UpdateProductStatusUseCase updateProductStatusUseCase(ProductRepository productRepository) {
        return new UpdateProductStatusUseCaseImpl(productRepository);
    }

    @Bean
    public DeleteProductUseCase deleteProductUseCase(
            ProductRepository productRepository
    ) {
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
