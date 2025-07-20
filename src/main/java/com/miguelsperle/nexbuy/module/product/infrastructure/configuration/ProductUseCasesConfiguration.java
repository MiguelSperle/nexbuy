package com.miguelsperle.nexbuy.module.product.infrastructure.configuration;

import com.miguelsperle.nexbuy.module.product.application.usecases.*;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.*;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IBrandGateway;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.ICategoryGateway;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IColorGateway;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IProductGateway;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.providers.ISkuProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductUseCasesConfiguration {
    @Bean
    public IRegisterProductUseCase registerProductUseCase(
            IProductGateway productGateway,
            ICategoryGateway categoryGateway,
            IBrandGateway brandGateway,
            IColorGateway colorGateway,
            ISkuProvider skuProvider
    ) {
        return new RegisterProductUseCase(
                productGateway,
                categoryGateway,
                brandGateway,
                colorGateway,
                skuProvider
        );
    }

    @Bean
    public IUpdateProductUseCase updateProductUseCase(
            IProductGateway productGateway,
            ICategoryGateway categoryGateway,
            IBrandGateway brandGateway,
            IColorGateway colorGateway,
            ISkuProvider skuProvider
    ) {
        return new UpdateProductUseCase(
                productGateway,
                categoryGateway,
                brandGateway,
                colorGateway,
                skuProvider
        );
    }

    @Bean
    public IUpdateProductStatusUseCase updateProductStatusUseCase(IProductGateway productGateway) {
        return new UpdateProductStatusUseCase(productGateway);
    }

    @Bean
    public IDeleteProductUseCase deleteProductUseCase(IProductGateway productGateway) {
        return new DeleteProductUseCase(productGateway);
    }

    @Bean
    public IGetProductsUseCase getProductsUseCase(IProductGateway productGateway) {
        return new GetProductsUseCase(productGateway);
    }
}
