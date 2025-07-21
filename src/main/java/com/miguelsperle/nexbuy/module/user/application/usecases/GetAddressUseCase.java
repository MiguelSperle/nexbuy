package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.GetAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.GetAddressUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.AddressNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IGetAddressUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IAddressGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.Address;

public class GetAddressUseCase implements IGetAddressUseCase {
    private final IAddressGateway addressGateway;

    public GetAddressUseCase(IAddressGateway addressGateway) {
        this.addressGateway = addressGateway;
    }

    @Override
    public GetAddressUseCaseOutput execute(GetAddressUseCaseInput getAddressUseCaseInput) {
        final Address address = this.getAddressById(getAddressUseCaseInput.addressId());

        return GetAddressUseCaseOutput.from(address);
    }

    private Address getAddressById(String addressId) {
        return this.addressGateway.findById(addressId)
                .orElseThrow(() -> AddressNotFoundException.with("Address not found"));
    }
}
