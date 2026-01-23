package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.AddressRepository;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.GetAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.GetAddressUseCaseOutput;
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
public class GetAddressUseCaseTest {
    @InjectMocks
    private GetAddressUseCaseImpl getAddressUseCase;

    @Mock
    private AddressRepository addressRepository;

    @Test
    @DisplayName("Should get address")
    public void should_get_address() {
        final User user = UserBuilderTest.create(
                UserStatus.VERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );
        final Address address = AddressBuilderTest.create(user.getId());

        Mockito.when(this.addressRepository.findById(Mockito.any())).thenReturn(Optional.of(address));

        final GetAddressUseCaseInput getAddressUseCaseInput = GetAddressUseCaseInput.with(address.getId());

        final GetAddressUseCaseOutput getAddressUseCaseOutput = this.getAddressUseCase.execute(getAddressUseCaseInput);

        Assertions.assertNotNull(getAddressUseCaseOutput);
        Assertions.assertNotNull(getAddressUseCaseOutput.address());
        Assertions.assertEquals(address, getAddressUseCaseOutput.address());

        Mockito.verify(this.addressRepository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when address does not exist")
    public void should_throw_NotFoundException_when_address_does_not_exist() {
        Mockito.when(this.addressRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final GetAddressUseCaseInput getAddressUseCaseInput = GetAddressUseCaseInput.with(Mockito.any());

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.getAddressUseCase.execute(getAddressUseCaseInput)
        );

        final String expectedErrorMessage = "Address not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.addressRepository, Mockito.times(1)).findById(Mockito.any());
    }
}
