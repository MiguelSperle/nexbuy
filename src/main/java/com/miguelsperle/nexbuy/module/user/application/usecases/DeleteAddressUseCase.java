package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.DeleteAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.AddressNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IDeleteAddressUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IAddressGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.Address;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteAddressUseCase implements IDeleteAddressUseCase {
    private final IAddressGateway addressGateway;

    @Override
    public void execute(DeleteAddressUseCaseInput deleteAddressUseCaseInput) {
        final Address address = this.getAddressById(deleteAddressUseCaseInput.getAddressId());

        this.addressGateway.deleteById(address.getId());
    }

    private Address getAddressById(String addressId) {
        return this.addressGateway.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException("Address not found"));
    }
}
