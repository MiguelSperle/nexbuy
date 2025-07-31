package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.application.ports.out.security.SecurityContextService;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.ports.in.CreateAddressUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.AddressRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.Address;

public class CreateAddressUseCaseImpl implements CreateAddressUseCase {
    private final AddressRepository addressRepository;
    private final SecurityContextService securityContextService;

    public CreateAddressUseCaseImpl(
            final AddressRepository addressRepository,
            final SecurityContextService securityContextService
    ) {
        this.addressRepository = addressRepository;
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
        this.addressRepository.save(address);
    }
}
