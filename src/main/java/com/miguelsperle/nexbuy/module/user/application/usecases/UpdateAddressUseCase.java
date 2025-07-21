package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.exceptions.AddressNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IUpdateAddressUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.UpdateAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IAddressGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.Address;

public class UpdateAddressUseCase implements IUpdateAddressUseCase {
    private final IAddressGateway addressGateway;

    public UpdateAddressUseCase(IAddressGateway addressGateway) {
        this.addressGateway = addressGateway;
    }

    @Override
    public void execute(UpdateAddressUseCaseInput updateAddressUseCaseInput) {
        final Address address = this.getAddressById(updateAddressUseCaseInput.addressId());

        final Address updatedAddress = address.withAddressLine(updateAddressUseCaseInput.addressLine())
                .withAddressNumber(updateAddressUseCaseInput.addressNumber())
                .withZipCode(updateAddressUseCaseInput.zipCode())
                .withNeighborhood(updateAddressUseCaseInput.neighborhood())
                .withCity(updateAddressUseCaseInput.city())
                .withUf(updateAddressUseCaseInput.uf())
                .withComplement(updateAddressUseCaseInput.complement());

        this.saveAddress(updatedAddress);
    }

    private Address getAddressById(String addressId) {
        return this.addressGateway.findById(addressId)
                .orElseThrow(() -> AddressNotFoundException.with("Address not found"));
    }

    private void saveAddress(Address address) {
        this.addressGateway.save(address);
    }
}
