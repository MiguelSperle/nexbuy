package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.DeleteAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.AddressNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.ports.in.DeleteAddressUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.AddressRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.Address;

public class DeleteAddressUseCaseImpl implements DeleteAddressUseCase {
    private final AddressRepository addressRepository;

    public DeleteAddressUseCaseImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public void execute(DeleteAddressUseCaseInput deleteAddressUseCaseInput) {
        final Address address = this.getAddressById(deleteAddressUseCaseInput.addressId());

        this.deleteAddressById(address.getId());
    }

    private Address getAddressById(String addressId) {
        return this.addressRepository.findById(addressId)
                .orElseThrow(() -> AddressNotFoundException.with("Address not found"));
    }

    private void deleteAddressById(String addressId) {
        this.addressRepository.deleteById(addressId);
    }
}
