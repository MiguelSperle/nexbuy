package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ColorRepository;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.UpdateColorUseCaseInput;
import com.miguelsperle.nexbuy.module.product.domain.entities.Color;
import com.miguelsperle.nexbuy.module.product.utils.ColorBuilderTest;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;
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
public class UpdateColorUseCaseTest {
    @InjectMocks
    private UpdateColorUseCaseImpl updateColorUseCase;

    @Mock
    private ColorRepository colorRepository;

    @Test
    @DisplayName("Should update color")
    public void should_update_color() {
        final Color color = ColorBuilderTest.create();

        Mockito.when(this.colorRepository.findById(Mockito.any())).thenReturn(Optional.of(color));

        final UpdateColorUseCaseInput updateColorUseCaseInput = UpdateColorUseCaseInput.with(
                color.getId(),
                color.getName()
        );

        final Color updateColor = color.withName(updateColorUseCaseInput.name());

        Mockito.when(this.colorRepository.save(Mockito.any())).thenReturn(updateColor);

        this.updateColorUseCase.execute(updateColorUseCaseInput);

        Mockito.verify(this.colorRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.colorRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when color does not exist")
    public void should_throw_NotFoundException_when_color_does_not_exist() {
        final Color color = ColorBuilderTest.create();

        Mockito.when(this.colorRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final UpdateColorUseCaseInput updateColorUseCaseInput = UpdateColorUseCaseInput.with(
                color.getId(),
                color.getName()
        );

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.updateColorUseCase.execute(updateColorUseCaseInput)
        );

        final String expectedErrorMessage = "Color not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.colorRepository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    @DisplayName("Should throw DomainException when color with same name already exists")
    public void should_throw_DomainException_when_color_with_same_name_already_exists() {
        final Color color = ColorBuilderTest.create();
        final String colorNewName = "test-new-color-name";

        Mockito.when(this.colorRepository.findById(Mockito.any())).thenReturn(Optional.of(color));

        Mockito.when(this.colorRepository.existsByName(Mockito.any())).thenReturn(true);

        final UpdateColorUseCaseInput updateColorUseCaseInput = UpdateColorUseCaseInput.with(
                color.getId(),
                colorNewName
        );

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.updateColorUseCase.execute(updateColorUseCaseInput)
        );

        final String expectedErrorMessage = "Color with this name already exists";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.colorRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.colorRepository, Mockito.times(1)).existsByName(Mockito.any());
    }
}
