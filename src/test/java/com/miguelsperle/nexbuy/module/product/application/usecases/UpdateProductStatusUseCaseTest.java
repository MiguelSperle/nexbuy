package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ProductRepository;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.UpdateProductStatusUseCaseInput;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;
import com.miguelsperle.nexbuy.module.product.domain.entities.Color;
import com.miguelsperle.nexbuy.module.product.domain.entities.Product;
import com.miguelsperle.nexbuy.module.product.domain.enums.ProductStatus;
import com.miguelsperle.nexbuy.module.product.utils.BrandBuilderTest;
import com.miguelsperle.nexbuy.module.product.utils.CategoryBuilderTest;
import com.miguelsperle.nexbuy.module.product.utils.ColorBuilderTest;
import com.miguelsperle.nexbuy.module.product.utils.ProductBuilderTest;
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
public class UpdateProductStatusUseCaseTest {
    @InjectMocks
    private UpdateProductStatusUseCaseImpl updateProductStatusUseCase;

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("Should update product status")
    public void should_update_product_status() {
        final Category category = CategoryBuilderTest.create();
        final Brand brand = BrandBuilderTest.create();
        final Color color = ColorBuilderTest.create();
        final Product product = ProductBuilderTest.create(
                category.getId(), brand.getId(), color.getId(), ProductStatus.ACTIVE
        );

        Mockito.when(this.productRepository.findById(Mockito.any())).thenReturn(Optional.of(product));

        final Product updatedProduct = product.withProductStatus(ProductStatus.INACTIVE);

        Mockito.when(this.productRepository.save(Mockito.any())).thenReturn(updatedProduct);

        final UpdateProductStatusUseCaseInput updateProductStatusUseCaseInput = UpdateProductStatusUseCaseInput.with(
                product.getId(),
                product.getProductStatus().name()
        );

        this.updateProductStatusUseCase.execute(updateProductStatusUseCaseInput);

        Mockito.verify(this.productRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.productRepository, Mockito.times(1)).save(Mockito.any());
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

        final UpdateProductStatusUseCaseInput updateProductStatusUseCaseInput = UpdateProductStatusUseCaseInput.with(
                product.getId(),
                product.getProductStatus().name()
        );

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.updateProductStatusUseCase.execute(updateProductStatusUseCaseInput)
        );

        final String expectedErrorMessage = "Product not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.productRepository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    @DisplayName("Should throw DomainException when set status deleted")
    public void should_throw_DomainException_when_set_status_deleted() {
        final Category category = CategoryBuilderTest.create();
        final Brand brand = BrandBuilderTest.create();
        final Color color = ColorBuilderTest.create();
        final Product product = ProductBuilderTest.create(
                category.getId(), brand.getId(), color.getId(), ProductStatus.DELETED
        );

        final UpdateProductStatusUseCaseInput updateProductStatusUseCaseInput = UpdateProductStatusUseCaseInput.with(
                product.getId(),
                product.getProductStatus().name()
        );

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.updateProductStatusUseCase.execute(updateProductStatusUseCaseInput)
        );

        final String expectedErrorMessage = "Status DELETED is not allowed to be set";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Should throw DomainException when product has already been deleted")
    public void should_throw_DomainException_when_product_has_already_been_deleted() {
        final Category category = CategoryBuilderTest.create();
        final Brand brand = BrandBuilderTest.create();
        final Color color = ColorBuilderTest.create();
        final Product product = ProductBuilderTest.create(
                category.getId(), brand.getId(), color.getId(), ProductStatus.DELETED
        );

        Mockito.when(this.productRepository.findById(Mockito.any())).thenReturn(Optional.of(product));

        final UpdateProductStatusUseCaseInput updateProductStatusUseCaseInput = UpdateProductStatusUseCaseInput.with(
                product.getId(),
                ProductStatus.ACTIVE.name()
        );

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.updateProductStatusUseCase.execute(updateProductStatusUseCaseInput)
        );

        final String expectedErrorMessage = "This product has already been deleted and cannot be updated";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
