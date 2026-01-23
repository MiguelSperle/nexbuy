package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.BrandRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ProductRepository;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.DeleteBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;
import com.miguelsperle.nexbuy.module.product.utils.BrandBuilderTest;
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
public class DeleteBrandUseCaseTest {
    @InjectMocks
    private DeleteBrandUseCaseImpl deleteBrandUseCase;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("Should delete brand")
    public void should_delete_brand() {
        final Brand brand = BrandBuilderTest.create();

        Mockito.when(this.brandRepository.findById(Mockito.any())).thenReturn(Optional.of(brand));

        Mockito.when(this.productRepository.existsByBrandId(Mockito.any())).thenReturn(false);

        Mockito.doNothing().when(this.brandRepository).deleteById(Mockito.any());

        final DeleteBrandUseCaseInput deleteBrandUseCaseInput = DeleteBrandUseCaseInput.with(brand.getId());

        this.deleteBrandUseCase.execute(deleteBrandUseCaseInput);

        Mockito.verify(this.brandRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.productRepository, Mockito.times(1)).existsByBrandId(Mockito.any());
        Mockito.verify(this.brandRepository, Mockito.times(1)).deleteById(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when brand does not exist")
    public void should_throw_NotFoundException_when_brand_does_not_exist() {
        final Brand brand = BrandBuilderTest.create();

        Mockito.when(this.brandRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final DeleteBrandUseCaseInput deleteBrandUseCaseInput = DeleteBrandUseCaseInput.with(brand.getId());

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.deleteBrandUseCase.execute(deleteBrandUseCaseInput)
        );

        final String expectedErrorMessage = "Brand not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.brandRepository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    @DisplayName("Should throw DomainException when trying to delete brand with associated products")
    public void should_throw_DomainException_when_trying_to_delete_brand_with_associated_products() {
        final Brand brand = BrandBuilderTest.create();

        Mockito.when(this.brandRepository.findById(Mockito.any())).thenReturn(Optional.of(brand));

        Mockito.when(this.productRepository.existsByBrandId(Mockito.any())).thenReturn(true);

        final DeleteBrandUseCaseInput deleteBrandUseCaseInput = DeleteBrandUseCaseInput.with(brand.getId());

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.deleteBrandUseCase.execute(deleteBrandUseCaseInput)
        );

        final String expectedErrorMessage = "Brand cannot be deleted because it is already associated with products";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.brandRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.productRepository, Mockito.times(1)).existsByBrandId(Mockito.any());
    }
}
