package com.miguelsperle.nexbuy.module.product.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.module.product.application.ports.in.*;
import com.miguelsperle.nexbuy.module.product.application.usecases.*;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.IColorRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.IProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ColorUseCasesConfiguration {
    @Bean
    public IRegisterColorUseCase registerColorUseCase(IColorRepository colorRepository) {
        return new RegisterColorUseCase(colorRepository);
    }

    @Bean
    public IUpdateColorUseCase updateColorUseCase(IColorRepository colorRepository) {
        return new UpdateColorUseCase(colorRepository);
    }

    @Bean
    public IDeleteColorUseCase deleteColorUseCase(IColorRepository colorRepository, IProductRepository productRepository) {
        return new DeleteColorUseCase(colorRepository, productRepository);
    }

    @Bean
    public IGetColorUseCase getColorUseCase(IColorRepository colorRepository) {
        return new GetColorUseCase(colorRepository);
    }

    @Bean
    public IGetColorsUseCase getColorsUseCase(IColorRepository colorRepository) {
        return new GetColorsUseCase(colorRepository);
    }
}
