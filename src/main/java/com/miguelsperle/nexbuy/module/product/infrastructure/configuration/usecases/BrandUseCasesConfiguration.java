package com.miguelsperle.nexbuy.module.product.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.module.product.application.ports.in.*;
import com.miguelsperle.nexbuy.module.product.application.usecases.*;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.IBrandRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.IProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrandUseCasesConfiguration {
    @Bean
    public IRegisterBrandUseCase registerBrandUseCase(IBrandRepository brandRepository) {
        return new RegisterBrandUseCase(brandRepository);
    }

    @Bean
    public IUpdateBrandUseCase updateBrandUseCase(IBrandRepository brandRepository) {
        return new UpdateBrandUseCase(brandRepository);
    }

    @Bean
    public IDeleteBrandUseCase deleteBrandUseCase(IBrandRepository brandRepository, IProductRepository productRepository) {
        return new DeleteBrandUseCase(brandRepository, productRepository);
    }

    @Bean
    public IGetBrandsUseCase getBrandsUseCase(IBrandRepository brandRepository) {
        return new GetBrandsUseCase(brandRepository);
    }

    @Bean
    public IGetBrandUseCase getBrandUseCase(IBrandRepository brandRepository) {
        return new GetBrandUseCase(brandRepository);
    }
}
