package com.miguelsperle.nexbuy.module.product.infrastructure.configuration;

import com.miguelsperle.nexbuy.module.product.application.usecases.RegisterColorUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IRegisterColorUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IColorGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ColorUseCasesConfiguration {
    @Bean
    public IRegisterColorUseCase registerColorUseCase(IColorGateway colorGateway) {
        return new RegisterColorUseCase(colorGateway);
    }
}
