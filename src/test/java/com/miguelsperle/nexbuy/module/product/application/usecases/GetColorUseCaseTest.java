package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ColorRepository;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetColorUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetColorUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.domain.entities.Color;
import com.miguelsperle.nexbuy.module.product.utils.ColorBuilderTest;
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
public class GetColorUseCaseTest {
    @InjectMocks
    private GetColorUseCaseImpl getColorUseCase;

    @Mock
    private ColorRepository colorRepository;

    @Test
    @DisplayName("Should get color")
    public void should_get_color() {
        final Color color = ColorBuilderTest.create();

        Mockito.when(this.colorRepository.findById(Mockito.any())).thenReturn(Optional.of(color));

        final GetColorUseCaseInput getColorUseCaseInput = GetColorUseCaseInput.with(color.getId());

        final GetColorUseCaseOutput getColorUseCaseOutput = this.getColorUseCase.execute(getColorUseCaseInput);

        Assertions.assertNotNull(getColorUseCaseOutput);
        Assertions.assertNotNull(getColorUseCaseOutput.color());

        Assertions.assertEquals(color, getColorUseCaseOutput.color());

        Mockito.verify(this.colorRepository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when color does not exist")
    public void should_throw_NotFoundException_when_color_does_not_exist() {
        final Color color = ColorBuilderTest.create();

        Mockito.when(this.colorRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final GetColorUseCaseInput getColorUseCaseInput = GetColorUseCaseInput.with(color.getId());

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.getColorUseCase.execute(getColorUseCaseInput)
        );

        final String expectedErrorMessage = "Color not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.colorRepository, Mockito.times(1)).findById(Mockito.any());
    }
}
