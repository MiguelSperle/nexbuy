package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.BrandRepository;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.RegisterBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;
import com.miguelsperle.nexbuy.module.product.utils.BrandBuilderTest;
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
public class RegisterBrandUseCaseTest {
    @InjectMocks
    private RegisterBrandUseCaseImpl registerBrandUseCase;

    @Mock
    private BrandRepository brandRepository;

    @Test
    @DisplayName("Should register brand")
    public void should_register_brand() {
        final Brand brand = BrandBuilderTest.create();

        Mockito.when(this.brandRepository.existsByName(Mockito.any())).thenReturn(false);

        Mockito.when(this.brandRepository.save(Mockito.any())).thenReturn(brand);

        final RegisterBrandUseCaseInput registerBrandUseCaseInput = RegisterBrandUseCaseInput.with(brand.getName());

        this.registerBrandUseCase.execute(registerBrandUseCaseInput);

        Mockito.verify(this.brandRepository, Mockito.times(1)).existsByName(Mockito.any());
        Mockito.verify(this.brandRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Should throw DomainException when brand with same name already exists")
    public void should_throw_DomainException_when_brand_with_same_name_already_exists() {
        final Brand brand = BrandBuilderTest.create();

        Mockito.when(this.brandRepository.existsByName(Mockito.any())).thenReturn(true);

        final RegisterBrandUseCaseInput registerBrandUseCaseInput = RegisterBrandUseCaseInput.with(brand.getName());

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.registerBrandUseCase.execute(registerBrandUseCaseInput)
        );

        final String expectedErrorMessage = "Brand with this name already exists";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.brandRepository, Mockito.times(1)).existsByName(Mockito.any());
    }
}
