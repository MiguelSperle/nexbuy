package com.miguelsperle.nexbuy.module.user.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.module.user.application.usecases.CreateNaturalPersonUseCaseImpl;
import com.miguelsperle.nexbuy.module.user.application.ports.in.CreateNaturalPersonUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.NaturalPersonRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NaturalPersonUseCasesConfiguration {
    @Bean
    public CreateNaturalPersonUseCase createNaturalPersonUseCase(
            NaturalPersonRepository naturalPersonRepository
    ) {
        return new CreateNaturalPersonUseCaseImpl(naturalPersonRepository);
    }
}
