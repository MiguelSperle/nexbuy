package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.GetAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.GetAddressUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.AddressNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.ports.in.IGetAddressUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.IAddressRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.Address;

public class GetAddressUseCase implements IGetAddressUseCase {
    private final IAddressRepository addressRepository;

    public GetAddressUseCase(IAddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public GetAddressUseCaseOutput execute(GetAddressUseCaseInput getAddressUseCaseInput) {
        final Address address = this.getAddressById(getAddressUseCaseInput.addressId());

        return GetAddressUseCaseOutput.from(address);
    }

    private Address getAddressById(String addressId) {
        return this.addressRepository.findById(addressId)
                .orElseThrow(() -> AddressNotFoundException.with("Address not found"));
    }
}
