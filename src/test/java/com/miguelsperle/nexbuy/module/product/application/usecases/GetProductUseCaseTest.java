package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ProductRepository;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetProductUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetProductUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;
import com.miguelsperle.nexbuy.module.product.domain.entities.Color;
import com.miguelsperle.nexbuy.module.product.domain.entities.Product;
import com.miguelsperle.nexbuy.module.product.domain.enums.ProductStatus;
import com.miguelsperle.nexbuy.module.product.utils.BrandBuilderTest;
import com.miguelsperle.nexbuy.module.product.utils.CategoryBuilderTest;
import com.miguelsperle.nexbuy.module.product.utils.ColorBuilderTest;
import com.miguelsperle.nexbuy.module.product.utils.ProductBuilderTest;
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
public class GetProductUseCaseTest {
    @InjectMocks
    private GetProductUseCaseImpl getProductUseCase;

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("Should get product")
    public void should_get_product() {
        final Category category = CategoryBuilderTest.create();
        final Brand brand = BrandBuilderTest.create();
        final Color color = ColorBuilderTest.create();
        final Product product = ProductBuilderTest.create(
                category.getId(), brand.getId(), color.getId(), ProductStatus.ACTIVE
        );

        Mockito.when(this.productRepository.findById(Mockito.any())).thenReturn(Optional.of(product));

        final GetProductUseCaseInput getProductUseCaseInput = GetProductUseCaseInput.with(product.getId());

        final GetProductUseCaseOutput getProductUseCaseOutput = this.getProductUseCase.execute(getProductUseCaseInput);

        Assertions.assertNotNull(getProductUseCaseOutput);
        Assertions.assertNotNull(getProductUseCaseOutput.product());

        Assertions.assertEquals(product, getProductUseCaseOutput.product());

        Mockito.verify(this.productRepository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when product does not exist")
    public void should_throw_NotFoundException_when_product_does_not_exist() {
        final Category category = CategoryBuilderTest.create();
        final Brand brand = BrandBuilderTest.create();
        final Color color = ColorBuilderTest.create();
        final Product product = ProductBuilderTest.create(
                category.getId(), brand.getId(), color.getId(), ProductStatus.ACTIVE
        );

        Mockito.when(this.productRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final GetProductUseCaseInput getProductUseCaseInput = GetProductUseCaseInput.with(product.getId());

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.getProductUseCase.execute(getProductUseCaseInput)
        );

        final String expectedErrorMessage = "Product not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.productRepository, Mockito.times(1)).findById(Mockito.any());
    }
}
