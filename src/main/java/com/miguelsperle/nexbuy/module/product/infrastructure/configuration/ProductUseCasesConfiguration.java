package com.miguelsperle.nexbuy.module.product.infrastructure.configuration;

import com.miguelsperle.nexbuy.module.product.application.usecases.RegisterProductUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.UpdateProductUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IRegisterProductUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IUpdateProductUseCase;
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
}
