package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.AddressRepository;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.DeleteAddressUseCaseInput;
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
public class DeleteAddressUseCaseTest {
    @InjectMocks
    private DeleteAddressUseCaseImpl deleteAddressUseCase;

    @Mock
    private AddressRepository addressRepository;

    @Test
    @DisplayName("Should delete address when it exists")
    public void should_delete_address_when_it_exists() {
        final User user = UserBuilderTest.create(
                UserStatus.VERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );
        final Address address = AddressBuilderTest.create(user.getId());

        Mockito.when(this.addressRepository.findById(Mockito.any())).thenReturn(Optional.of(address));

        Mockito.doNothing().when(this.addressRepository).deleteById(Mockito.any());

        final DeleteAddressUseCaseInput deleteAddressUseCaseInput = DeleteAddressUseCaseInput.with(address.getId());

        this.deleteAddressUseCase.execute(deleteAddressUseCaseInput);

        Mockito.verify(this.addressRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.addressRepository, Mockito.times(1)).deleteById(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when address does not exist")
    public void should_throw_NotFoundException_when_address_does_not_exist() {
        final User user = UserBuilderTest.create(
                UserStatus.VERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );
        final Address address = AddressBuilderTest.create(user.getId());

        Mockito.when(this.addressRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final DeleteAddressUseCaseInput deleteAddressUseCaseInput = DeleteAddressUseCaseInput.with(address.getId());

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.deleteAddressUseCase.execute(deleteAddressUseCaseInput)
        );

        final String expectedErrorMessage = "Address not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.addressRepository, Mockito.times(1)).findById(Mockito.any());
    }
}
