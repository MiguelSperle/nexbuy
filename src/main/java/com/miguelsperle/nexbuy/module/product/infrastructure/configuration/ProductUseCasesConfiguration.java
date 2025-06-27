package com.miguelsperle.nexbuy.module.product.infrastructure.configuration;

import com.miguelsperle.nexbuy.module.product.application.usecases.GetProductBrandsUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.RegisterProductBrandUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.RegisterProductCategoryUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IGetProductBrandsUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IRegisterProductBrandUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IRegisterProductCategoryUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IProductBrandGateway;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IProductCategoryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductUseCasesConfiguration {
    @Bean
    public IRegisterProductBrandUseCase registerProductBrandUseCase(IProductBrandGateway productBrandGateway) {
        return new RegisterProductBrandUseCase(productBrandGateway);
    }

    @Bean
    public IGetProductBrandsUseCase getProductBrandsUseCase(IProductBrandGateway productBrandGateway) {
        return new GetProductBrandsUseCase(productBrandGateway);
    }

    @Bean
    public IRegisterProductCategoryUseCase registerProductCategoryUseCase(IProductCategoryGateway productCategoryGateway) {
        return new RegisterProductCategoryUseCase(productCategoryGateway);
    }
}
