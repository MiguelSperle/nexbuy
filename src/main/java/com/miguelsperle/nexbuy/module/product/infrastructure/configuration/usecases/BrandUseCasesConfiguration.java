package com.miguelsperle.nexbuy.module.product.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.module.product.application.ports.in.usecases.*;
import com.miguelsperle.nexbuy.module.product.application.usecases.*;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.BrandRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ProductRepository;
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
