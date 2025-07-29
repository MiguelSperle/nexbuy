package com.miguelsperle.nexbuy.module.product.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.core.application.ports.out.transaction.ITransactionExecutor;
import com.miguelsperle.nexbuy.module.product.application.ports.in.*;
import com.miguelsperle.nexbuy.module.product.application.usecases.*;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.IBrandRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ICategoryRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.IColorRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.IProductRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.providers.ISkuProvider;
import com.miguelsperle.nexbuy.module.stock.application.ports.in.ICreateStockUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductUseCasesConfiguration {
    @Bean
    public IRegisterProductUseCase registerProductUseCase(
            IProductRepository productRepository,
            ICategoryRepository categoryRepository,
            IBrandRepository brandRepository,
            IColorRepository colorRepository,
            ISkuProvider skuProvider,
            ICreateStockUseCase createStockUseCase,
            ITransactionExecutor transactionExecutor
    ) {
        return new RegisterProductUseCase(
                productRepository,
                categoryRepository,
                brandRepository,
                colorRepository,
                skuProvider,
                createStockUseCase,
                transactionExecutor
        );
    }

    @Bean
    public IUpdateProductUseCase updateProductUseCase(
            IProductRepository productRepository,
            ICategoryRepository categoryRepository,
            IBrandRepository brandRepository,
            IColorRepository colorRepository,
            ISkuProvider skuProvider
    ) {
        return new UpdateProductUseCase(
                productRepository,
                categoryRepository,
                brandRepository,
                colorRepository,
                skuProvider
        );
    }

    @Bean
    public IUpdateProductStatusUseCase updateProductStatusUseCase(IProductRepository productRepository) {
        return new UpdateProductStatusUseCase(productRepository);
    }

    @Bean
    public IDeleteProductUseCase deleteProductUseCase(IProductRepository productRepository) {
        return new DeleteProductUseCase(productRepository);
    }

    @Bean
    public IGetProductsUseCase getProductsUseCase(IProductRepository productRepository) {
        return new GetProductsUseCase(productRepository);
    }

    @Bean
    public IGetProductUseCase getProductUseCase(IProductRepository productRepository) {
        return new GetProductUseCase(productRepository);
    }
}
