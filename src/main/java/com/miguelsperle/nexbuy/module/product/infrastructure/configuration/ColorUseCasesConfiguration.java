package com.miguelsperle.nexbuy.module.product.infrastructure.configuration;

import com.miguelsperle.nexbuy.module.product.application.usecases.DeleteColorUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.GetColorUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.RegisterColorUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.UpdateColorUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IDeleteColorUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IGetColorUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IRegisterColorUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IUpdateColorUseCase;
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
}
