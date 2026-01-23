package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ColorRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ProductRepository;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.DeleteColorUseCaseInput;
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
public class DeleteColorUseCaseTest {
    @InjectMocks
    private DeleteColorUseCaseImpl deleteColorUseCase;

    @Mock
    private ColorRepository colorRepository;

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("Should delete color")
    public void should_delete_color() {
        final Color color = ColorBuilderTest.create();

        Mockito.when(this.colorRepository.findById(Mockito.any())).thenReturn(Optional.of(color));

        Mockito.when(this.productRepository.existsByColorId(Mockito.any())).thenReturn(false);

        Mockito.doNothing().when(this.colorRepository).deleteById(Mockito.any());

        final DeleteColorUseCaseInput deleteColorUseCaseInput = DeleteColorUseCaseInput.with(color.getId());

        this.deleteColorUseCase.execute(deleteColorUseCaseInput);

        Mockito.verify(this.colorRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.productRepository, Mockito.times(1)).existsByColorId(Mockito.any());
        Mockito.verify(this.colorRepository, Mockito.times(1)).deleteById(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when color does not exist")
    public void should_throw_NotFoundException_when_color_does_not_exist() {
        final Color color = ColorBuilderTest.create();

        Mockito.when(this.colorRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final DeleteColorUseCaseInput deleteColorUseCaseInput = DeleteColorUseCaseInput.with(color.getId());

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.deleteColorUseCase.execute(deleteColorUseCaseInput)
        );

        final String expectedErrorMessage = "Color not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.colorRepository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    @DisplayName("Should throw DomainException when trying to delete color with associated products")
    public void should_throw_DomainException_when_trying_to_delete_color_with_associated_products() {
        final Color color = ColorBuilderTest.create();

        Mockito.when(this.colorRepository.findById(Mockito.any())).thenReturn(Optional.of(color));

        Mockito.when(this.productRepository.existsByColorId(Mockito.any())).thenReturn(true);

        final DeleteColorUseCaseInput deleteColorUseCaseInput = DeleteColorUseCaseInput.with(color.getId());

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.deleteColorUseCase.execute(deleteColorUseCaseInput)
        );

        final String expectedErrorMessage = "Color cannot be deleted because it is already associated with products";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.colorRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.productRepository, Mockito.times(1)).existsByColorId(Mockito.any());
    }
}
