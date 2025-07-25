package com.miguelsperle.nexbuy.module.user.infrastructure.configuration;

import com.miguelsperle.nexbuy.core.domain.abstractions.security.ISecurityContextService;
import com.miguelsperle.nexbuy.module.user.application.usecases.CreateAddressUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateAddressUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.DeleteAddressUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IDeleteAddressUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.GetAddressUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IGetAddressUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.GetAddressesUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IGetAddressesUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IUpdateAddressUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.UpdateAddressUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IAddressGateway;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AddressUseCasesConfiguration {
    @Bean
    public ICreateAddressUseCase createAddressUseCase(
            IAddressGateway addressGateway,
            ISecurityContextService securityContextService
    ) {
        return new CreateAddressUseCase(addressGateway, securityContextService);
    }

    @Bean
    public IUpdateAddressUseCase updateAddressUseCase(
            IAddressGateway addressGateway
    ) {
        return new UpdateAddressUseCase(addressGateway);
    }

    @Bean
    public IGetAddressesUseCase getAddressesUseCase(
            IAddressGateway addressGateway,
            ISecurityContextService securityContextService,
            IUserGateway userGateway
    ) {
        return new GetAddressesUseCase(
                addressGateway,
                securityContextService,
                userGateway
        );
    }

    @Bean
    public IGetAddressUseCase getAddressUseCase(IAddressGateway addressGateway) {
        return new GetAddressUseCase(addressGateway);
    }

    @Bean
    public IDeleteAddressUseCase deleteAddressUseCase(IAddressGateway addressGateway) {
        return new DeleteAddressUseCase(addressGateway);
    }
}
