package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.GetAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.GetAddressUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.AddressNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IGetAddressUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IAddressGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.Address;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAddressUseCase implements IGetAddressUseCase {
    private final IAddressGateway addressGateway;

    @Override
    public GetAddressUseCaseOutput execute(GetAddressUseCaseInput getAddressUseCaseInput) {
        final Address address = this.getAddressById(getAddressUseCaseInput.getAddressId());

        return new GetAddressUseCaseOutput(address);
    }

    private Address getAddressById(String addressId) {
        return this.addressGateway.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException("Address not found"));
    }
}
