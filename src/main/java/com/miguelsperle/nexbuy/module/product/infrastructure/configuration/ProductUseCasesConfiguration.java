package com.miguelsperle.nexbuy.module.product.infrastructure.configuration;

import com.miguelsperle.nexbuy.module.product.application.usecases.GetBrandsUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.GetCategoriesUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.RegisterBrandUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.RegisterCategoryUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IGetBrandsUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IGetCategoriesUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IRegisterBrandUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IRegisterCategoryUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IBrandGateway;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.ICategoryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductUseCasesConfiguration {
    @Bean
    public IRegisterBrandUseCase registerBrandUseCase(IBrandGateway brandGateway) {
        return new RegisterBrandUseCase(brandGateway);
    }

    @Bean
    public IGetBrandsUseCase getProductBrandsUseCase(IBrandGateway productBrandGateway) {
        return new GetBrandsUseCase(productBrandGateway);
    }

    @Bean
    public IRegisterCategoryUseCase registerCategoryUseCase(ICategoryGateway categoryGateway) {
        return new RegisterCategoryUseCase(categoryGateway);
    }

    @Bean
    public IGetCategoriesUseCase getCategoriesUseCase(ICategoryGateway categoryGateway) {
        return new GetCategoriesUseCase(categoryGateway);
    }
}
