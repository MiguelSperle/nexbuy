package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.BrandRepository;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetBrandUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;
import com.miguelsperle.nexbuy.module.product.utils.BrandBuilderTest;
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
public class GetBrandUseCaseTest {
    @InjectMocks
    private GetBrandUseCaseImpl getBrandUseCase;

    @Mock
    private BrandRepository brandRepository;

    @Test
    @DisplayName("Should get brand")
    public void should_get_brand() {
        final Brand brand = BrandBuilderTest.create();

        Mockito.when(this.brandRepository.findById(Mockito.any())).thenReturn(Optional.of(brand));

        final GetBrandUseCaseInput getBrandUseCaseInput = GetBrandUseCaseInput.with(brand.getId());

        final GetBrandUseCaseOutput getBrandUseCaseOutput = this.getBrandUseCase.execute(getBrandUseCaseInput);

        Assertions.assertNotNull(getBrandUseCaseOutput);
        Assertions.assertNotNull(getBrandUseCaseOutput.brand());

        Assertions.assertEquals(brand, getBrandUseCaseOutput.brand());

        Mockito.verify(this.brandRepository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when brand does not exist")
    public void should_throw_NotFoundException_when_brand_does_not_exist() {
        final Brand brand = BrandBuilderTest.create();

        Mockito.when(this.brandRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final GetBrandUseCaseInput getBrandUseCaseInput = GetBrandUseCaseInput.with(brand.getId());

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.getBrandUseCase.execute(getBrandUseCaseInput)
        );

        final String expectedErrorMessage = "Brand not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.brandRepository, Mockito.times(1)).findById(Mockito.any());
    }
}
