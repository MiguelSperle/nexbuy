package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ColorRepository;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.RegisterColorUseCaseInput;
import com.miguelsperle.nexbuy.module.product.domain.entities.Color;
import com.miguelsperle.nexbuy.module.product.utils.ColorBuilderTest;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RegisterColorUseCaseTest {
    @InjectMocks
    private RegisterColorUseCaseImpl registerColorUseCase;

    @Mock
    private ColorRepository colorRepository;

    @Test
    @DisplayName("Should register color")
    public void should_register_color() {
        final Color color = ColorBuilderTest.create();

        Mockito.when(this.colorRepository.existsByName(Mockito.any())).thenReturn(false);

        Mockito.when(this.colorRepository.save(Mockito.any())).thenReturn(color);

        final RegisterColorUseCaseInput registerColorUseCaseInput = RegisterColorUseCaseInput.with(color.getName());

        this.registerColorUseCase.execute(registerColorUseCaseInput);

        Mockito.verify(this.colorRepository, Mockito.times(1)).existsByName(Mockito.any());
        Mockito.verify(this.colorRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Should throw DomainException when color with same name already exists")
    public void should_throw_DomainException_when_color_with_same_name_already_exists() {
        final Color color = ColorBuilderTest.create();

        Mockito.when(this.colorRepository.existsByName(Mockito.any())).thenReturn(true);

        final RegisterColorUseCaseInput registerColorUseCaseInput = RegisterColorUseCaseInput.with(color.getName());

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.registerColorUseCase.execute(registerColorUseCaseInput)
        );

        final String expectedErrorMessage = "Color with this name already exists";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.colorRepository, Mockito.times(1)).existsByName(Mockito.any());
    }
}
