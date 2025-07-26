package com.miguelsperle.nexbuy.module.user.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.module.user.application.usecases.CreateLegalPersonUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.in.ICreateLegalPersonUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.ILegalPersonRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LegalPersonUseCasesConfiguration {
    @Bean
    public ICreateLegalPersonUseCase createLegalPersonUseCase(
            ILegalPersonRepository legalPersonRepository
    ) {
        return new CreateLegalPersonUseCase(legalPersonRepository);
    }
}
