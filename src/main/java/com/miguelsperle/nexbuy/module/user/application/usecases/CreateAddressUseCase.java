package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.security.ISecurityContextService;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateAddressUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IAddressGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.Address;

public class CreateAddressUseCase implements ICreateAddressUseCase {
    private final IAddressGateway addressGateway;
    private final ISecurityContextService securityContextService;

    public CreateAddressUseCase(
            final IAddressGateway addressGateway,
            final ISecurityContextService securityContextService
    ) {
        this.addressGateway = addressGateway;
        this.securityContextService = securityContextService;
    }

    @Override
    public void execute(CreateAddressUseCaseInput createAddressUseCaseInput) {
        final String authenticatedUserId = this.getAuthenticatedUserId();

        final Address newAddress = Address.newAddress(
                authenticatedUserId,
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

    private String getAuthenticatedUserId() {
        return this.securityContextService.getAuthenticatedUserId();
    }

    private void saveAddress(Address address) {
        this.addressGateway.save(address);
    }
}
