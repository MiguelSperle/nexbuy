package com.miguelsperle.nexbuy.module.product.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.module.product.application.ports.in.*;
import com.miguelsperle.nexbuy.module.product.application.usecases.*;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ICategoryRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.IProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryUseCasesConfiguration {
    @Bean
    public IRegisterCategoryUseCase registerCategoryUseCase(ICategoryRepository categoryRepository) {
        return new RegisterCategoryUseCase(categoryRepository);
    }

    @Bean
    public IUpdateCategoryUseCase updateCategoryUseCase(ICategoryRepository categoryRepository) {
        return new UpdateCategoryUseCase(categoryRepository);
    }

    @Bean
    public IDeleteCategoryUseCase deleteCategoryUseCase(ICategoryRepository categoryRepository, IProductRepository productRepository) {
        return new DeleteCategoryUseCase(categoryRepository, productRepository);
    }

    @Bean
    public IGetCategoriesUseCase getCategoriesUseCase(ICategoryRepository categoryRepository) {
        return new GetCategoriesUseCase(categoryRepository);
    }

    @Bean
    public IGetCategoryUseCase getCategoryUseCase(ICategoryRepository categoryRepository) {
        return new GetCategoryUseCase(categoryRepository);
    }
}
