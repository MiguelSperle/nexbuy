package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.application.exceptions.AuthenticatedUserNotFoundException;
import com.miguelsperle.nexbuy.core.domain.abstractions.security.IAuthenticatedUserService;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.CreateAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateAddressUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IAddressGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.Address;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateAddressUseCase implements ICreateAddressUseCase {
    private final IAddressGateway addressGateway;
    private final IAuthenticatedUserService authenticatedUserService;

    @Override
    public void execute(CreateAddressUseCaseInput createAddressUseCaseInput) {
        final User authenticatedUser = this.getAuthenticatedUser();

        final Address newAddress = Address.newAddress(
                authenticatedUser,
                createAddressUseCaseInput.getAddressLine(),
                createAddressUseCaseInput.getAddressNumber(),
                createAddressUseCaseInput.getZipCode(),
                createAddressUseCaseInput.getNeighborhood(),
                createAddressUseCaseInput.getCity(),
                createAddressUseCaseInput.getUf(),
                createAddressUseCaseInput.getComplement()
        );

        this.addressGateway.save(newAddress);
    }

    private User getAuthenticatedUser() {
        return this.authenticatedUserService.getAuthenticatedUser()
                .orElseThrow(() -> new AuthenticatedUserNotFoundException("Authenticated user not found in security context"));
    }
}
