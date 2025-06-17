package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.UpdateAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.AddressNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IUpdateAddressUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IAddressGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.Address;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateAddressUseCase implements IUpdateAddressUseCase {
    private final IAddressGateway addressGateway;

    @Override
    public void execute(UpdateAddressUseCaseInput updateAddressUseCaseInput) {
        final Address address = this.getAddressById(updateAddressUseCaseInput.getAddressId());

        final Address addressUpdated = address.withAddressLine(updateAddressUseCaseInput.getAddressLine())
                .withAddressNumber(updateAddressUseCaseInput.getAddressNumber())
                .withZipCode(updateAddressUseCaseInput.getZipCode())
                .withNeighborhood(updateAddressUseCaseInput.getNeighborhood())
                .withCity(updateAddressUseCaseInput.getCity())
                .withUf(updateAddressUseCaseInput.getUf())
                .withComplement(updateAddressUseCaseInput.getComplement());

        this.addressGateway.save(addressUpdated);
    }

    private Address getAddressById(String addressId) {
        return this.addressGateway.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException("Address not found"));
    }
}
