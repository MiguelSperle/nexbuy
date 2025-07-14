package com.miguelsperle.nexbuy.module.product.infrastructure.configuration;

import com.miguelsperle.nexbuy.module.product.application.usecases.*;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.*;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IColorGateway;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IProductGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ColorUseCasesConfiguration {
    @Bean
    public IRegisterColorUseCase registerColorUseCase(IColorGateway colorGateway) {
        return new RegisterColorUseCase(colorGateway);
    }

    @Bean
    public IUpdateColorUseCase updateColorUseCase(IColorGateway colorGateway) {
        return new UpdateColorUseCase(colorGateway);
    }

    @Bean
    public IDeleteColorUseCase deleteColorUseCase(IColorGateway colorGateway, IProductGateway productGateway) {
        return new DeleteColorUseCase(colorGateway, productGateway);
    }

    @Bean
    public IGetColorUseCase getColorUseCase(IColorGateway colorGateway) {
        return new GetColorUseCase(colorGateway);
    }

    @Bean
    public IGetColorsUseCase getColorsUseCase(IColorGateway colorGateway) {
        return new GetColorsUseCase(colorGateway);
    }
}
