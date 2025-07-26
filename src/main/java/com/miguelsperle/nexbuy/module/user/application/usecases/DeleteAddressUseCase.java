package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.DeleteAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.AddressNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.ports.in.IDeleteAddressUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.IAddressRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.Address;

public class DeleteAddressUseCase implements IDeleteAddressUseCase {
    private final IAddressRepository addressRepository;

    public DeleteAddressUseCase(IAddressRepository addressRepository) {
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
