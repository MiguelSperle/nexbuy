package com.miguelsperle.nexbuy.module.product.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.module.product.application.ports.in.*;
import com.miguelsperle.nexbuy.module.product.application.usecases.*;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.CategoryRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryUseCasesConfiguration {
    @Bean
    public RegisterCategoryUseCase registerCategoryUseCase(CategoryRepository categoryRepository) {
        return new RegisterCategoryUseCaseImpl(categoryRepository);
    }

    @Bean
    public UpdateCategoryUseCase updateCategoryUseCase(CategoryRepository categoryRepository) {
        return new UpdateCategoryUseCaseImpl(categoryRepository);
    }

    @Bean
    public DeleteCategoryUseCase deleteCategoryUseCase(CategoryRepository categoryRepository, ProductRepository productRepository) {
        return new DeleteCategoryUseCaseImpl(categoryRepository, productRepository);
    }

    @Bean
    public GetCategoriesUseCase getCategoriesUseCase(CategoryRepository categoryRepository) {
        return new GetCategoriesUseCaseImpl(categoryRepository);
    }

    @Bean
    public GetCategoryUseCase getCategoryUseCase(CategoryRepository categoryRepository) {
        return new GetCategoryUseCaseImpl(categoryRepository);
    }
}
