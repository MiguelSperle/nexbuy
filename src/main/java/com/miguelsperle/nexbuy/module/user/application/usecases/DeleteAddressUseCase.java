package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.DeleteAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.AddressNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IDeleteAddressUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IAddressGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.Address;

public class DeleteAddressUseCase implements IDeleteAddressUseCase {
    private final IAddressGateway addressGateway;

    public DeleteAddressUseCase(IAddressGateway addressGateway) {
        this.addressGateway = addressGateway;
    }

    @Override
    public void execute(DeleteAddressUseCaseInput deleteAddressUseCaseInput) {
        final Address address = this.getAddressById(deleteAddressUseCaseInput.addressId());

        this.deleteAddressById(address.getId());
    }

    private Address getAddressById(String addressId) {
        return this.addressGateway.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException("Address not found"));
    }

    private void deleteAddressById(String addressId) {
        this.addressGateway.deleteById(addressId);
    }
}
