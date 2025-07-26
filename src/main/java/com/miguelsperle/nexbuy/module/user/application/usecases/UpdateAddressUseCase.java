package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.domain.exceptions.AddressNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.ports.in.IUpdateAddressUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.UpdateAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.IAddressRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.Address;

public class UpdateAddressUseCase implements IUpdateAddressUseCase {
    private final IAddressRepository addressRepository;

    public UpdateAddressUseCase(IAddressRepository addressRepository) {
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
                .orElseThrow(() -> AddressNotFoundException.with("Address not found"));
    }

    private void saveAddress(Address address) {
        this.addressRepository.save(address);
    }
}
