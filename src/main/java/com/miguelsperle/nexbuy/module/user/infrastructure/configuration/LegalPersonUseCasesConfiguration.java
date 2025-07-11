package com.miguelsperle.nexbuy.module.user.infrastructure.configuration;

import com.miguelsperle.nexbuy.module.user.application.usecases.CreateLegalPersonUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateLegalPersonUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.ILegalPersonGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LegalPersonUseCasesConfiguration {
    @Bean
    public ICreateLegalPersonUseCase createLegalPersonUseCase(
            ILegalPersonGateway legalPersonGateway
    ) {
        return new CreateLegalPersonUseCase(legalPersonGateway);
    }
}
