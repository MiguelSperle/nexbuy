package com.miguelsperle.nexbuy.module.freight.application.usecases;

import com.miguelsperle.nexbuy.module.freight.application.ports.out.persistence.FreightRepository;
import com.miguelsperle.nexbuy.module.freight.application.usecases.io.inputs.GetFreightUseCaseInput;
import com.miguelsperle.nexbuy.module.freight.application.usecases.io.outputs.GetFreightUseCaseOutput;
import com.miguelsperle.nexbuy.module.freight.domain.entities.Freight;
import com.miguelsperle.nexbuy.module.freight.utils.FreightBuilderTest;
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
public class GetFreightUseCaseTest {
    @InjectMocks
    private GetFreightUseCaseImpl getFreightUseCase;

    @Mock
    private FreightRepository freightRepository;

    @Test
    @DisplayName("Should get freight")
    public void should_get_freight() {
        final Freight freight = FreightBuilderTest.create();

        Mockito.when(this.freightRepository.findByOrderId(Mockito.any())).thenReturn(Optional.of(freight));

        final GetFreightUseCaseInput getFreightUseCaseInput = GetFreightUseCaseInput.with(freight.getOrderId());

        final GetFreightUseCaseOutput getFreightUseCaseOutput = this.getFreightUseCase.execute(getFreightUseCaseInput);

        Assertions.assertNotNull(getFreightUseCaseOutput);
        Assertions.assertNotNull(getFreightUseCaseOutput.freight());

        Assertions.assertEquals(freight, getFreightUseCaseOutput.freight());

        Mockito.verify(this.freightRepository, Mockito.times(1)).findByOrderId(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when freight does not exist")
    public void should_throw_NotFoundException_when_freight_does_not_exist() {
        final Freight freight = FreightBuilderTest.create();

        Mockito.when(this.freightRepository.findByOrderId(Mockito.any())).thenReturn(Optional.empty());

        final GetFreightUseCaseInput getFreightUseCaseInput = GetFreightUseCaseInput.with(freight.getOrderId());

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.getFreightUseCase.execute(getFreightUseCaseInput)
        );

        final String expectedErrorMessage = "Freight not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.freightRepository, Mockito.times(1)).findByOrderId(Mockito.any());
    }
}
