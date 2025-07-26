package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.application.ports.out.security.ISecurityContextService;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.ports.in.ICreateAddressUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.IAddressRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.Address;

public class CreateAddressUseCase implements ICreateAddressUseCase {
    private final IAddressRepository addressRepository;
    private final ISecurityContextService securityContextService;

    public CreateAddressUseCase(
            final IAddressRepository addressRepository,
            final ISecurityContextService securityContextService
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
