package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.AddressRepository;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.domain.entities.Address;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.enums.AuthorizationRole;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserStatus;
import com.miguelsperle.nexbuy.module.user.utils.AddressBuilderTest;
import com.miguelsperle.nexbuy.module.user.utils.UserBuilderTest;
import com.miguelsperle.nexbuy.shared.application.ports.out.services.SecurityContextService;
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
    private SecurityContextService securityContextService;

    @Test
    @DisplayName("Should create address")
    public void should_create_address() {
        final User user = UserBuilderTest.create(
                UserStatus.VERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );
        final Address address = AddressBuilderTest.create(user.getId());

        Mockito.when(this.securityContextService.getAuthenticatedUserId()).thenReturn(user.getId());

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

        Mockito.verify(this.securityContextService, Mockito.times(1)).getAuthenticatedUserId();
        Mockito.verify(this.addressRepository, Mockito.times(1)).save(Mockito.any());
    }
}
