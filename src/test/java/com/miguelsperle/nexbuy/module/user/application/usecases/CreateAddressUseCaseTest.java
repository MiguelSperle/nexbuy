package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.abstractions.repositories.AddressRepository;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.domain.entities.Address;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.enums.AuthorizationRole;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserStatus;
import com.miguelsperle.nexbuy.module.user.utils.AddressBuilderTest;
import com.miguelsperle.nexbuy.module.user.utils.UserBuilderTest;
import com.miguelsperle.nexbuy.shared.application.abstractions.services.SecurityService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateAddressUseCaseTest {
    @InjectMocks
    private CreateAddressUseCaseImpl createAddressUseCase;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private SecurityService securityService;

    @Test
    @DisplayName("Should create address")
    public void should_create_address() {
        final User user = UserBuilderTest.create(
                UserStatus.VERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );
        final Address address = AddressBuilderTest.create(user.getId());

        Mockito.when(this.securityService.getUserId()).thenReturn(user.getId());

        Mockito.when(this.addressRepository.save(Mockito.any())).thenReturn(address);

        final CreateAddressUseCaseInput createAddressUseCaseInput = CreateAddressUseCaseInput.with(
                address.getAddressLine(),
                address.getAddressNumber(),
                address.getZipCode(),
                address.getNeighborhood(),
                address.getCity(),
                address.getUf(),
                address.getComplement()
        );

        this.createAddressUseCase.execute(createAddressUseCaseInput);

        Mockito.verify(this.securityService, Mockito.times(1)).getUserId();
        Mockito.verify(this.addressRepository, Mockito.times(1)).save(Mockito.any());
    }
}
