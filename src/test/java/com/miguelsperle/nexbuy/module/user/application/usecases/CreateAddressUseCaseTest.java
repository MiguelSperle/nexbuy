package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.AddressRepository;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.domain.entities.Address;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.utils.mocks.AddressMock;
import com.miguelsperle.nexbuy.module.user.utils.mocks.UserMock;
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
    @DisplayName("Should be able to create address")
    public void should_be_able_to_create_address() {
        final User userMock = UserMock.create();
        final Address addressMock = AddressMock.create(userMock.getId());

        Mockito.when(this.securityContextService.getAuthenticatedUserId()).thenReturn(userMock.getId());

        Mockito.when(this.addressRepository.save(Mockito.any())).thenReturn(addressMock);

        final CreateAddressUseCaseInput createAddressUseCaseInput = CreateAddressUseCaseInput.with(
                addressMock.getAddressLine(),
                addressMock.getAddressNumber(),
                addressMock.getZipCode(),
                addressMock.getNeighborhood(),
                addressMock.getCity(),
                addressMock.getUf(),
                addressMock.getComplement()
        );

        this.createAddressUseCase.execute(createAddressUseCaseInput);

        Mockito.verify(this.securityContextService, Mockito.times(1)).getAuthenticatedUserId();
        Mockito.verify(this.addressRepository, Mockito.times(1)).save(Mockito.any());
    }
}
