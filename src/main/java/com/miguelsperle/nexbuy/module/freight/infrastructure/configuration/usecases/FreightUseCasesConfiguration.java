package com.miguelsperle.nexbuy.module.freight.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.module.freight.application.ports.in.usecases.ConsultFreightQuotesUseCase;
import com.miguelsperle.nexbuy.module.freight.application.ports.out.services.FreightService;
import com.miguelsperle.nexbuy.module.freight.application.usecases.ConsultFreightQuotesUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FreightUseCasesConfiguration {
    @Bean
    public ConsultFreightQuotesUseCase consultShippingUseCase(FreightService freightService) {
        return new ConsultFreightQuotesUseCaseImpl(freightService);
    }
}
