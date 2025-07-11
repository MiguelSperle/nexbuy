package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.application.exceptions.AuthenticatedUserNotFoundException;
import com.miguelsperle.nexbuy.core.domain.abstractions.security.IAuthenticatedUserService;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.CreateAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateAddressUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IAddressGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.Address;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;

public class CreateAddressUseCase implements ICreateAddressUseCase {
    private final IAddressGateway addressGateway;
    private final IAuthenticatedUserService authenticatedUserService;

    public CreateAddressUseCase(
            final IAddressGateway addressGateway,
            final IAuthenticatedUserService authenticatedUserService
    ) {
        this.addressGateway = addressGateway;
        this.authenticatedUserService = authenticatedUserService;
    }

    @Override
    public void execute(CreateAddressUseCaseInput createAddressUseCaseInput) {
        final User authenticatedUser = this.getAuthenticatedUser();

        final Address newAddress = Address.newAddress(
                authenticatedUser,
                createAddressUseCaseInput.addressLine(),
                createAddressUseCaseInput.addressNumber(),
                createAddressUseCaseInput.zipCode(),
                createAddressUseCaseInput.neighborhood(),
                createAddressUseCaseInput.city(),
                createAddressUseCaseInput.uf(),
                createAddressUseCaseInput.complement()
        );

        this.saveAddress(newAddress);
    }

    private User getAuthenticatedUser() {
        return this.authenticatedUserService.getAuthenticatedUser()
                .orElseThrow(() -> new AuthenticatedUserNotFoundException("Authenticated user not found in security context"));
    }

    private void saveAddress(Address address) {
        this.addressGateway.save(address);
    }
}
