package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.BrandRepository;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.UpdateBrandUseCaseInput;
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
public class UpdateBrandUseCaseTest {
    @InjectMocks
    private UpdateBrandUseCaseImpl updateBrandUseCase;

    @Mock
    private BrandRepository brandRepository;

    @Test
    @DisplayName("Should update brand")
    public void should_update_brand() {
        final Brand brand = BrandBuilderTest.create();

        Mockito.when(this.brandRepository.findById(Mockito.any())).thenReturn(Optional.of(brand));

        final Brand updatedBrand = brand.withName(brand.getName());

        Mockito.when(this.brandRepository.save(Mockito.any())).thenReturn(updatedBrand);

        final UpdateBrandUseCaseInput updateBrandUseCaseInput = UpdateBrandUseCaseInput.with(
                brand.getId(),
                brand.getName()
        );

        this.updateBrandUseCase.execute(updateBrandUseCaseInput);

        Mockito.verify(this.brandRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.brandRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when brand does not exist")
    public void should_throw_NotFoundException_when_brand_does_not_exist() {
        final Brand brand = BrandBuilderTest.create();

        Mockito.when(this.brandRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final UpdateBrandUseCaseInput updateBrandUseCaseInput = UpdateBrandUseCaseInput.with(
                brand.getId(),
                brand.getName()
        );

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.updateBrandUseCase.execute(updateBrandUseCaseInput)
        );

        final String expectedErrorMessage = "Brand not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.brandRepository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    @DisplayName("Should throw DomainException when brand with same name already exists")
    public void should_throw_DomainException_when_brand_with_same_name_already_exists() {
        final Brand brand = BrandBuilderTest.create();
        final String newBrandName = "test-new-brand-name";

        Mockito.when(this.brandRepository.findById(Mockito.any())).thenReturn(Optional.of(brand));

        Mockito.when(this.brandRepository.existsByName(Mockito.any())).thenReturn(true);

        final UpdateBrandUseCaseInput updateBrandUseCaseInput = UpdateBrandUseCaseInput.with(
                brand.getId(),
                newBrandName
        );

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.updateBrandUseCase.execute(updateBrandUseCaseInput)
        );

        final String expectedErrorMessage = "Brand with this name already exists";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.brandRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.brandRepository, Mockito.times(1)).existsByName(Mockito.any());
    }
}
