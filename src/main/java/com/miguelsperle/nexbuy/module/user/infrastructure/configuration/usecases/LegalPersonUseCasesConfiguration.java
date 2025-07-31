package com.miguelsperle.nexbuy.module.user.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.module.user.application.usecases.CreateLegalPersonUseCaseImpl;
import com.miguelsperle.nexbuy.module.user.application.ports.in.CreateLegalPersonUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.LegalPersonRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LegalPersonUseCasesConfiguration {
    @Bean
    public CreateLegalPersonUseCase createLegalPersonUseCase(
            LegalPersonRepository legalPersonRepository
    ) {
        return new CreateLegalPersonUseCaseImpl(legalPersonRepository);
    }
}
