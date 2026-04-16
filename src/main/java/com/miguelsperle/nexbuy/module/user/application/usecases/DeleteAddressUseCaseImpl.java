package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.DeleteAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.abstractions.usecases.DeleteAddressUseCase;
import com.miguelsperle.nexbuy.module.user.application.abstractions.repositories.AddressRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.Address;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

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
                .orElseThrow(() -> NotFoundException.with("Address not found"));
    }

    private void deleteAddressById(String addressId) {
        this.addressRepository.deleteById(addressId);
    }
}
