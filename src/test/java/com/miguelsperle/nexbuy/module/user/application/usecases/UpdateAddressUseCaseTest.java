package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.AddressRepository;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.UpdateAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.domain.entities.Address;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.enums.AuthorizationRole;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserStatus;
import com.miguelsperle.nexbuy.module.user.utils.AddressBuilderTest;
import com.miguelsperle.nexbuy.module.user.utils.UserBuilderTest;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UpdateAddressUseCaseTest {
    @InjectMocks
    private UpdateAddressUseCaseImpl updateAddressUseCase;

    @Mock
    private AddressRepository addressRepository;

    @Test
    @DisplayName("Should update address")
    public void should_update_address() {
        final User user = UserBuilderTest.create(
                UserStatus.VERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );
        final Address address = AddressBuilderTest.create(user.getId());

        Mockito.when(this.addressRepository.findById(Mockito.any())).thenReturn(Optional.of(address));

        final UpdateAddressUseCaseInput updateAddressUseCaseInput = UpdateAddressUseCaseInput.with(
                address.getId(),
                address.getAddressLine(),
                address.getAddressNumber(),
                address.getZipCode(),
                address.getNeighborhood(),
                address.getCity(),
                address.getUf(),
                address.getComplement()
        );

        final Address updatedAddress = address.withAddressLine(updateAddressUseCaseInput.addressLine())
                .withAddressNumber(updateAddressUseCaseInput.addressNumber())
                .withZipCode(updateAddressUseCaseInput.zipCode())
                .withNeighborhood(updateAddressUseCaseInput.neighborhood())
                .withCity(updateAddressUseCaseInput.city())
                .withUf(updateAddressUseCaseInput.uf())
                .withComplement(updateAddressUseCaseInput.complement());

        Mockito.when(this.addressRepository.save(Mockito.any())).thenReturn(updatedAddress);

        this.updateAddressUseCase.execute(updateAddressUseCaseInput);

        Mockito.verify(this.addressRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.addressRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when address does not exist")
    public void should_throw_NotFoundException_when_address_does_not_exist() {
        final User user = UserBuilderTest.create(
                UserStatus.VERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );
        final Address address = AddressBuilderTest.create(user.getId());

        Mockito.when(this.addressRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final UpdateAddressUseCaseInput updateAddressUseCaseInput = UpdateAddressUseCaseInput.with(
                address.getId(),
                address.getAddressLine(),
                address.getAddressNumber(),
                address.getZipCode(),
                address.getNeighborhood(),
                address.getCity(),
                address.getUf(),
                address.getComplement()
        );

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.updateAddressUseCase.execute(updateAddressUseCaseInput)
        );

        final String expectedErrorMessage = "Address not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.addressRepository, Mockito.times(1)).findById(Mockito.any());
    }
}
