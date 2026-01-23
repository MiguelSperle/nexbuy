package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.GetAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.GetAddressUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.ports.in.usecases.GetAddressUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.AddressRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.Address;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

public class GetAddressUseCaseImpl implements GetAddressUseCase {
    private final AddressRepository addressRepository;

    public GetAddressUseCaseImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public GetAddressUseCaseOutput execute(GetAddressUseCaseInput getAddressUseCaseInput) {
        final Address address = this.getAddressById(getAddressUseCaseInput.addressId());

        return GetAddressUseCaseOutput.from(address);
    }

    private Address getAddressById(String addressId) {
        return this.addressRepository.findById(addressId)
                .orElseThrow(() -> NotFoundException.with("Address not found"));
    }
}
