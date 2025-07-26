package com.miguelsperle.nexbuy.module.user.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.core.application.ports.out.security.ISecurityContextService;
import com.miguelsperle.nexbuy.module.user.application.usecases.CreateAddressUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.in.ICreateAddressUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.DeleteAddressUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.in.IDeleteAddressUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.GetAddressUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.in.IGetAddressUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.GetAddressesUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.in.IGetAddressesUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.in.IUpdateAddressUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.UpdateAddressUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.IAddressRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.IUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AddressUseCasesConfiguration {
    @Bean
    public ICreateAddressUseCase createAddressUseCase(
            IAddressRepository addressRepository,
            ISecurityContextService securityContextService
    ) {
        return new CreateAddressUseCase(addressRepository, securityContextService);
    }

    @Bean
    public IUpdateAddressUseCase updateAddressUseCase(
            IAddressRepository addressRepository
    ) {
        return new UpdateAddressUseCase(addressRepository);
    }

    @Bean
    public IGetAddressesUseCase getAddressesUseCase(
            IAddressRepository addressRepository,
            ISecurityContextService securityContextService,
            IUserRepository userRepository
    ) {
        return new GetAddressesUseCase(
                addressRepository,
                securityContextService,
                userRepository
        );
    }

    @Bean
    public IGetAddressUseCase getAddressUseCase(IAddressRepository addressRepository) {
        return new GetAddressUseCase(addressRepository);
    }

    @Bean
    public IDeleteAddressUseCase deleteAddressUseCase(IAddressRepository addressRepository) {
        return new DeleteAddressUseCase(addressRepository);
    }
}
