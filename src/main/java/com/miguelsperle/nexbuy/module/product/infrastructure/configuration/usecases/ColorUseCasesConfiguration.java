package com.miguelsperle.nexbuy.module.product.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.module.product.application.ports.in.*;
import com.miguelsperle.nexbuy.module.product.application.usecases.*;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ColorRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ColorUseCasesConfiguration {
    @Bean
    public RegisterColorUseCase registerColorUseCase(ColorRepository colorRepository) {
        return new RegisterColorUseCaseImpl(colorRepository);
    }

    @Bean
    public UpdateColorUseCase updateColorUseCase(ColorRepository colorRepository) {
        return new UpdateColorUseCaseImpl(colorRepository);
    }

    @Bean
    public DeleteColorUseCase deleteColorUseCase(ColorRepository colorRepository, ProductRepository productRepository) {
        return new DeleteColorUseCaseImpl(colorRepository, productRepository);
    }

    @Bean
    public GetColorUseCase getColorUseCase(ColorRepository colorRepository) {
        return new GetColorUseCaseImpl(colorRepository);
    }

    @Bean
    public GetColorsUseCase getColorsUseCase(ColorRepository colorRepository) {
        return new GetColorsUseCaseImpl(colorRepository);
    }
}
