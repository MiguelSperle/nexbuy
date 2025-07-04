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
    public IGetBrandsUseCase getBrandsUseCase(IBrandGateway productBrandGateway) {
        return new GetBrandsUseCase(productBrandGateway);
    }

    @Bean
    public IUpdateBrandNameUseCase updateBrandNameUseCase(IBrandGateway brandGateway) {
        return new UpdateBrandNameUseCase(brandGateway);
    }

    @Bean
    public IDeleteBrandUseCase deleteBrandUseCase(IBrandGateway brandGateway, IProductGateway productGateway) {
        return new DeleteBrandUseCase(brandGateway, productGateway);
    }

    @Bean
    public IRegisterRootCategoryUseCase registerRootCategoryUseCase(ICategoryGateway categoryGateway) {
        return new RegisterRootCategoryUseCase(categoryGateway);
    }

    @Bean
    public IRegisterSubCategoryUseCase registerSubCategoryUseCase(ICategoryGateway categoryGateway) {
        return new RegisterSubCategoryUseCase(categoryGateway);
    }

    @Bean
    public IGetCategoriesUseCase getCategoriesUseCase(ICategoryGateway categoryGateway) {
        return new GetCategoriesUseCase(categoryGateway);
    }
}
