package com.miguelsperle.nexbuy.module.product.infrastructure.configuration;

import com.miguelsperle.nexbuy.module.product.application.usecases.*;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.*;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IBrandGateway;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.ICategoryGateway;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IProductGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductUseCasesConfiguration {
    @Bean
    public IRegisterBrandUseCase registerBrandUseCase(IBrandGateway brandGateway) {
        return new RegisterBrandUseCase(brandGateway);
    }

    @Bean
    public IUpdateBrandUseCase updateBrandUseCase(IBrandGateway brandGateway) {
        return new UpdateBrandUseCase(brandGateway);
    }

    @Bean
    public IDeleteBrandUseCase deleteBrandUseCase(IBrandGateway brandGateway, IProductGateway productGateway) {
        return new DeleteBrandUseCase(brandGateway, productGateway);
    }

    @Bean
    public IGetBrandsUseCase getBrandsUseCase(IBrandGateway productBrandGateway) {
        return new GetBrandsUseCase(productBrandGateway);
    }

    @Bean
    public IGetBrandUseCase getBrandUseCase(IBrandGateway brandGateway) {
        return new GetBrandUseCase(brandGateway);
    }

    @Bean
    public IRegisterCategoryUseCase registerCategoryUseCase(ICategoryGateway categoryGateway) {
        return new RegisterCategoryUseCase(categoryGateway);
    }

    @Bean
    public IUpdateCategoryUseCase updateCategoryUseCase(ICategoryGateway categoryGateway) {
        return new UpdateCategoryUseCase(categoryGateway);
    }
}
