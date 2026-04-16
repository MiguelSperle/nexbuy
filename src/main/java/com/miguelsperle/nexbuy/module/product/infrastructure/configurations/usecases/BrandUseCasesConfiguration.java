package com.miguelsperle.nexbuy.module.product.infrastructure.configurations.usecases;

import com.miguelsperle.nexbuy.module.product.application.abstractions.usecases.*;
import com.miguelsperle.nexbuy.module.product.application.usecases.*;
import com.miguelsperle.nexbuy.module.product.application.abstractions.repositories.BrandRepository;
import com.miguelsperle.nexbuy.module.product.application.abstractions.repositories.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrandUseCasesConfiguration {
    @Bean
    public RegisterBrandUseCase registerBrandUseCase(BrandRepository brandRepository) {
        return new RegisterBrandUseCaseImpl(brandRepository);
    }

    @Bean
    public UpdateBrandUseCase updateBrandUseCase(BrandRepository brandRepository) {
        return new UpdateBrandUseCaseImpl(brandRepository);
    }

    @Bean
    public DeleteBrandUseCase deleteBrandUseCase(BrandRepository brandRepository, ProductRepository productRepository) {
        return new DeleteBrandUseCaseImpl(brandRepository, productRepository);
    }

    @Bean
    public GetBrandsUseCase getBrandsUseCase(BrandRepository brandRepository) {
        return new GetBrandsUseCaseImpl(brandRepository);
    }

    @Bean
    public GetBrandUseCase getBrandUseCase(BrandRepository brandRepository) {
        return new GetBrandUseCaseImpl(brandRepository);
    }
}
