package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.BrandRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.CategoryRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ColorRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ProductRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.providers.SkuProvider;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.UpdateProductUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.complements.DimensionComplementInput;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;
import com.miguelsperle.nexbuy.module.product.domain.entities.Color;
import com.miguelsperle.nexbuy.module.product.domain.entities.Product;
import com.miguelsperle.nexbuy.module.product.domain.enums.ProductStatus;
import com.miguelsperle.nexbuy.module.product.utils.BrandBuilderTest;
import com.miguelsperle.nexbuy.module.product.utils.CategoryBuilderTest;
import com.miguelsperle.nexbuy.module.product.utils.ColorBuilderTest;
import com.miguelsperle.nexbuy.module.product.utils.ProductBuilderTest;
import com.miguelsperle.nexbuy.shared.application.ports.out.producer.MessageProducer;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;
import com.miguelsperle.nexbuy.shared.domain.utils.DecimalUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UpdateProductUseCaseTest {
    @InjectMocks
    private UpdateProductUseCaseImpl updateProductUseCase;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private ColorRepository colorRepository;

    @Mock
    private SkuProvider skuProvider;

    @Mock
    private MessageProducer messageProducer;

    @Test
    @DisplayName("Should update product")
    public void should_update_product() {
        final Category category = CategoryBuilderTest.create();
        final Brand brand = BrandBuilderTest.create();
        final Color color = ColorBuilderTest.create();
        final Product product = ProductBuilderTest.create(
                category.getId(), brand.getId(), color.getId(), ProductStatus.ACTIVE
        );
        final String newSku = "test-new-sku";
        final BigDecimal newPrice = DecimalUtils.valueOf(100);
        final String newName = "test-new-name";

        Mockito.when(this.productRepository.findById(Mockito.any())).thenReturn(Optional.of(product));

        Mockito.when(this.categoryRepository.findById(Mockito.any())).thenReturn(Optional.of(category));

        Mockito.when(this.brandRepository.findById(Mockito.any())).thenReturn(Optional.of(brand));

        Mockito.when(this.colorRepository.findById(Mockito.any())).thenReturn(Optional.of(color));

        Mockito.when(this.skuProvider.generateSku(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(newSku);

        final DimensionComplementInput dimensionComplementInput = DimensionComplementInput.with(
                product.getHeight(),
                product.getWidth(),
                product.getLength()
        );

        final UpdateProductUseCaseInput updateProductUseCaseInput = UpdateProductUseCaseInput.with(
                product.getId(),
                newName,
                product.getDescription(),
                category.getId(),
                newPrice,
                brand.getId(),
                color.getId(),
                product.getWeight(),
                dimensionComplementInput
        );

        final Product updatedProduct = product.withName(updateProductUseCaseInput.name())
                .withDescription(updateProductUseCaseInput.description())
                .withCategory(category.getId())
                .withPrice(updateProductUseCaseInput.price())
                .withSku(newSku)
                .withBrand(brand.getId())
                .withColor(color.getId())
                .withWeight(updateProductUseCaseInput.weight())
                .withHeight(updateProductUseCaseInput.dimensionComplementInput().height())
                .withWidth(updateProductUseCaseInput.dimensionComplementInput().width())
                .withLength(updateProductUseCaseInput.dimensionComplementInput().length());

        Mockito.when(this.productRepository.save(Mockito.any())).thenReturn(updatedProduct);

        Mockito.doNothing().when(this.messageProducer).publish(Mockito.any(), Mockito.any(), Mockito.any());

        this.updateProductUseCase.execute(updateProductUseCaseInput);

        Mockito.verify(this.productRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.categoryRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.brandRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.colorRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.skuProvider, Mockito.times(1)).generateSku(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.verify(this.messageProducer, Mockito.times(2)).publish(Mockito.any(), Mockito.any(), Mockito.any());
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

        final DimensionComplementInput dimensionComplementInput = DimensionComplementInput.with(
                product.getHeight(),
                product.getWidth(),
                product.getLength()
        );

        final UpdateProductUseCaseInput updateProductUseCaseInput = UpdateProductUseCaseInput.with(
                product.getId(),
                product.getName(),
                product.getDescription(),
                category.getId(),
                product.getPrice(),
                brand.getId(),
                color.getId(),
                product.getWeight(),
                dimensionComplementInput
        );

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.updateProductUseCase.execute(updateProductUseCaseInput)
        );

        final String expectedErrorMessage = "Product not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.productRepository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    @DisplayName("Should throw DomainException when try to update product to status deleted")
    public void should_throw_DomainException_when_try_to_update_product_to_status_deleted() {
        final Category category = CategoryBuilderTest.create();
        final Brand brand = BrandBuilderTest.create();
        final Color color = ColorBuilderTest.create();
        final Product product = ProductBuilderTest.create(
                category.getId(), brand.getId(), color.getId(), ProductStatus.DELETED
        );

        Mockito.when(this.productRepository.findById(Mockito.any())).thenReturn(Optional.of(product));

        final DimensionComplementInput dimensionComplementInput = DimensionComplementInput.with(
                product.getHeight(),
                product.getWidth(),
                product.getLength()
        );

        final UpdateProductUseCaseInput updateProductUseCaseInput = UpdateProductUseCaseInput.with(
                product.getId(),
                product.getName(),
                product.getDescription(),
                category.getId(),
                product.getPrice(),
                brand.getId(),
                color.getId(),
                product.getWeight(),
                dimensionComplementInput
        );

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.updateProductUseCase.execute(updateProductUseCaseInput)
        );

        final String expectedErrorMessage = "This product has already been deleted and cannot be updated";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.productRepository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when product category does not exist")
    public void should_throw_NotFoundException_when_product_category_does_not_exist() {
        final Category category = CategoryBuilderTest.create();
        final Brand brand = BrandBuilderTest.create();
        final Color color = ColorBuilderTest.create();
        final Product product = ProductBuilderTest.create(
                category.getId(), brand.getId(), color.getId(), ProductStatus.ACTIVE
        );

        Mockito.when(this.productRepository.findById(Mockito.any())).thenReturn(Optional.of(product));

        Mockito.when(this.categoryRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final DimensionComplementInput dimensionComplementInput = DimensionComplementInput.with(
                product.getHeight(),
                product.getWidth(),
                product.getLength()
        );

        final UpdateProductUseCaseInput updateProductUseCaseInput = UpdateProductUseCaseInput.with(
                product.getId(),
                product.getName(),
                product.getDescription(),
                category.getId(),
                product.getPrice(),
                brand.getId(),
                color.getId(),
                product.getWeight(),
                dimensionComplementInput
        );

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.updateProductUseCase.execute(updateProductUseCaseInput)
        );

        final String expectedErrorMessage = "Category not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.productRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.categoryRepository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when product brand does not exist")
    public void should_throw_NotFoundException_when_product_brand_does_not_exist() {
        final Category category = CategoryBuilderTest.create();
        final Brand brand = BrandBuilderTest.create();
        final Color color = ColorBuilderTest.create();
        final Product product = ProductBuilderTest.create(
                category.getId(), brand.getId(), color.getId(), ProductStatus.ACTIVE
        );

        Mockito.when(this.productRepository.findById(Mockito.any())).thenReturn(Optional.of(product));

        Mockito.when(this.categoryRepository.findById(Mockito.any())).thenReturn(Optional.of(category));

        Mockito.when(this.brandRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final DimensionComplementInput dimensionComplementInput = DimensionComplementInput.with(
                product.getHeight(),
                product.getWidth(),
                product.getLength()
        );

        final UpdateProductUseCaseInput updateProductUseCaseInput = UpdateProductUseCaseInput.with(
                product.getId(),
                product.getName(),
                product.getDescription(),
                category.getId(),
                product.getPrice(),
                brand.getId(),
                color.getId(),
                product.getWeight(),
                dimensionComplementInput
        );

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.updateProductUseCase.execute(updateProductUseCaseInput)
        );

        final String expectedErrorMessage = "Brand not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.productRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.categoryRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.brandRepository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when product color does not exist")
    public void should_throw_NotFoundException_when_product_color_does_not_exist() {
        final Category category = CategoryBuilderTest.create();
        final Brand brand = BrandBuilderTest.create();
        final Color color = ColorBuilderTest.create();
        final Product product = ProductBuilderTest.create(
                category.getId(), brand.getId(), color.getId(), ProductStatus.ACTIVE
        );

        Mockito.when(this.productRepository.findById(Mockito.any())).thenReturn(Optional.of(product));

        Mockito.when(this.categoryRepository.findById(Mockito.any())).thenReturn(Optional.of(category));

        Mockito.when(this.brandRepository.findById(Mockito.any())).thenReturn(Optional.of(brand));

        Mockito.when(this.colorRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final DimensionComplementInput dimensionComplementInput = DimensionComplementInput.with(
                product.getHeight(),
                product.getWidth(),
                product.getLength()
        );

        final UpdateProductUseCaseInput updateProductUseCaseInput = UpdateProductUseCaseInput.with(
                product.getId(),
                product.getName(),
                product.getDescription(),
                category.getId(),
                product.getPrice(),
                brand.getId(),
                color.getId(),
                product.getWeight(),
                dimensionComplementInput
        );

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.updateProductUseCase.execute(updateProductUseCaseInput)
        );

        final String expectedErrorMessage = "Color not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.productRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.categoryRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.brandRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.colorRepository, Mockito.times(1)).findById(Mockito.any());
    }
}
