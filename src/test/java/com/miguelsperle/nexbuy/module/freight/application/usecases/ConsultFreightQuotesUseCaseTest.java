package com.miguelsperle.nexbuy.module.freight.application.usecases;

import com.miguelsperle.nexbuy.module.freight.application.ports.out.services.FreightService;
import com.miguelsperle.nexbuy.module.freight.application.usecases.io.inputs.ConsultFreightQuotesUseCaseInput;
import com.miguelsperle.nexbuy.module.freight.application.usecases.io.inputs.complement.ItemsComplementInput;
import com.miguelsperle.nexbuy.module.freight.application.usecases.io.outputs.ConsultFreightQuotesUseCaseOutput;
import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ConsultFreightQuotesUseCaseTest {
    @InjectMocks
    private ConsultFreightQuotesUseCaseImpl consultFreightQuotesUseCase;

    @Mock
    private FreightService freightService;

    @Test
    @DisplayName("Should consult freight quotes")
    public void should_consult_freight_quotes() {
        final ItemsComplementInput itemsComplementInputs = ItemsComplementInput.with(
                IdentifierUtils.generateUUID(),
                100,
                100,
                100,
                10000,
                2
        );

        final ConsultFreightQuotesUseCaseInput consultFreightQuotesUseCaseInput = ConsultFreightQuotesUseCaseInput.with(
                "22400-100",
                List.of(itemsComplementInputs)
        );

        final String response = "some-response";

        Mockito.when(this.freightService.consult(Mockito.any(), Mockito.any())).thenReturn(response);

        final ConsultFreightQuotesUseCaseOutput consultFreightQuotesUseCaseOutput = this.consultFreightQuotesUseCase.execute(consultFreightQuotesUseCaseInput);

        Assertions.assertNotNull(consultFreightQuotesUseCaseOutput);
        Assertions.assertNotNull(consultFreightQuotesUseCaseOutput.response());

        Assertions.assertEquals(response, consultFreightQuotesUseCaseOutput.response());

        Mockito.verify(this.freightService, Mockito.times(1)).consult(Mockito.any(), Mockito.any());
    }
}
