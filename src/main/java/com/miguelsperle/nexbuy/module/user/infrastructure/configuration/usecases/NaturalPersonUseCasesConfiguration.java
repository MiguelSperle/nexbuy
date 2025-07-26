package com.miguelsperle.nexbuy.module.user.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.module.user.application.usecases.CreateNaturalPersonUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.in.ICreateNaturalPersonUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.INaturalPersonRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NaturalPersonUseCasesConfiguration {
    @Bean
    public ICreateNaturalPersonUseCase createNaturalPersonUseCase(
            INaturalPersonRepository naturalPersonRepository
    ) {
        return new CreateNaturalPersonUseCase(naturalPersonRepository);
    }
}
