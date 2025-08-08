package com.miguelsperle.nexbuy.module.user.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.shared.application.ports.out.security.SecurityContextService;
import com.miguelsperle.nexbuy.module.user.application.usecases.CreateAddressUseCaseImpl;
import com.miguelsperle.nexbuy.module.user.application.ports.in.CreateAddressUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.DeleteAddressUseCaseImpl;
import com.miguelsperle.nexbuy.module.user.application.ports.in.DeleteAddressUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.GetAddressUseCaseImpl;
import com.miguelsperle.nexbuy.module.user.application.ports.in.GetAddressUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.GetAddressesUseCaseImpl;
import com.miguelsperle.nexbuy.module.user.application.ports.in.GetAddressesUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.in.UpdateAddressUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.UpdateAddressUseCaseImpl;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.AddressRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AddressUseCasesConfiguration {
    @Bean
    public CreateAddressUseCase createAddressUseCase(
            AddressRepository addressRepository,
            SecurityContextService securityContextService
    ) {
        return new CreateAddressUseCaseImpl(addressRepository, securityContextService);
    }

    @Bean
    public UpdateAddressUseCase updateAddressUseCase(
            AddressRepository addressRepository
    ) {
        return new UpdateAddressUseCaseImpl(addressRepository);
    }

    @Bean
    public GetAddressesUseCase getAddressesUseCase(
            AddressRepository addressRepository,
            SecurityContextService securityContextService,
            UserRepository userRepository
    ) {
        return new GetAddressesUseCaseImpl(
                addressRepository,
                securityContextService,
                userRepository
        );
    }

    @Bean
    public GetAddressUseCase getAddressUseCase(AddressRepository addressRepository) {
        return new GetAddressUseCaseImpl(addressRepository);
    }

    @Bean
    public DeleteAddressUseCase deleteAddressUseCase(AddressRepository addressRepository) {
        return new DeleteAddressUseCaseImpl(addressRepository);
    }
}
