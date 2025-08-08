package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.ports.in.UpdateAddressUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.UpdateAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.AddressRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.Address;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

public class UpdateAddressUseCaseImpl implements UpdateAddressUseCase {
    private final AddressRepository addressRepository;

    public UpdateAddressUseCaseImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
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
        return this.addressRepository.findById(addressId)
                .orElseThrow(() -> NotFoundException.with("Address not found"));
    }

    private void saveAddress(Address address) {
        this.addressRepository.save(address);
    }
}
