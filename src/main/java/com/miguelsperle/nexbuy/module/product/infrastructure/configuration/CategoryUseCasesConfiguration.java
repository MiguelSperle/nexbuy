package com.miguelsperle.nexbuy.module.product.infrastructure.configuration;

import com.miguelsperle.nexbuy.module.product.application.usecases.*;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.*;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.ICategoryGateway;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IProductGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryUseCasesConfiguration {
    @Bean
    public IRegisterCategoryUseCase registerCategoryUseCase(ICategoryGateway categoryGateway) {
        return new RegisterCategoryUseCase(categoryGateway);
    }

    @Bean
    public IUpdateCategoryUseCase updateCategoryUseCase(ICategoryGateway categoryGateway) {
        return new UpdateCategoryUseCase(categoryGateway);
    }

    @Bean
    public IDeleteCategoryUseCase deleteCategoryUseCase(ICategoryGateway categoryGateway, IProductGateway productGateway) {
        return new DeleteCategoryUseCase(categoryGateway, productGateway);
    }

    @Bean
    public IGetCategoriesUseCase getCategoriesUseCase(ICategoryGateway categoryGateway) {
        return new GetCategoriesUseCase(categoryGateway);
    }

    @Bean
    public IGetCategoryUseCase getCategoryUseCase(ICategoryGateway categoryGateway) {
        return new GetCategoryUseCase(categoryGateway);
    }
}
