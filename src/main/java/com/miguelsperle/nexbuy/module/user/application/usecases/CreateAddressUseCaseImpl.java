package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.shared.application.abstractions.services.SecurityService;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.abstractions.usecases.CreateAddressUseCase;
import com.miguelsperle.nexbuy.module.user.application.abstractions.repositories.AddressRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.Address;

public class CreateAddressUseCaseImpl implements CreateAddressUseCase {
    private final AddressRepository addressRepository;
    private final SecurityService securityService;

    public CreateAddressUseCaseImpl(
            final AddressRepository addressRepository,
            final SecurityService securityService
    ) {
        this.addressRepository = addressRepository;
        this.securityService = securityService;
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
        return this.securityService.getUserId();
    }

    private void saveAddress(Address address) {
        this.addressRepository.save(address);
    }
}
