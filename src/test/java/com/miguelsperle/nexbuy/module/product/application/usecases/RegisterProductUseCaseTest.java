package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.BrandRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.CategoryRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ColorRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ProductRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.providers.SkuProvider;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.RegisterProductUseCaseInput;
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
public class RegisterProductUseCaseTest {
    @InjectMocks
    private RegisterProductUseCaseImpl registerProductUseCase;

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
    @DisplayName("Should register product")
    public void should_register_product() {
        final Category category = CategoryBuilderTest.create();
        final Brand brand = BrandBuilderTest.create();
        final Color color = ColorBuilderTest.create();
        final String sku = "test-sku";
        final Product product = ProductBuilderTest.create(
                category.getId(), brand.getId(), color.getId(), ProductStatus.ACTIVE
        );

        Mockito.when(this.categoryRepository.findById(Mockito.any())).thenReturn(Optional.of(category));

        Mockito.when(this.brandRepository.findById(Mockito.any())).thenReturn(Optional.of(brand));

        Mockito.when(this.colorRepository.findById(Mockito.any())).thenReturn(Optional.of(color));

        Mockito.when(this.skuProvider.generateSku(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(sku);

        Mockito.when(this.productRepository.save(Mockito.any())).thenReturn(product);

        Mockito.doNothing().when(this.messageProducer).publish(Mockito.any(), Mockito.any(), Mockito.any());

        final DimensionComplementInput dimensionComplementInput = DimensionComplementInput.with(
                product.getHeight(),
                product.getWidth(),
                product.getLength()
        );

        final RegisterProductUseCaseInput registerProductUseCaseInput = RegisterProductUseCaseInput.with(
                product.getName(),
                product.getDescription(),
                category.getId(),
                product.getPrice(),
                brand.getId(),
                color.getId(),
                product.getWeight(),
                dimensionComplementInput
        );

        this.registerProductUseCase.execute(registerProductUseCaseInput);

        Mockito.verify(this.categoryRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.brandRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.colorRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.skuProvider, Mockito.times(1)).generateSku(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.verify(this.productRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.messageProducer, Mockito.times(1)).publish(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when category does not exist")
    public void should_throw_NotFoundException_when_category_does_not_exist() {
        final Category category = CategoryBuilderTest.create();
        final Brand brand = BrandBuilderTest.create();
        final Color color = ColorBuilderTest.create();
        final Product product = ProductBuilderTest.create(
                category.getId(), brand.getId(), color.getId(), ProductStatus.ACTIVE
        );

        Mockito.when(this.categoryRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final DimensionComplementInput dimensionComplementInput = DimensionComplementInput.with(
                product.getHeight(),
                product.getWidth(),
                product.getLength()
        );

        final RegisterProductUseCaseInput registerProductUseCaseInput = RegisterProductUseCaseInput.with(
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
                this.registerProductUseCase.execute(registerProductUseCaseInput)
        );

        final String expectedErrorMessage = "Category not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.categoryRepository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when brand does not exist")
    public void should_throw_NotFoundException_when_brand_does_not_exist() {
        final Category category = CategoryBuilderTest.create();
        final Brand brand = BrandBuilderTest.create();
        final Color color = ColorBuilderTest.create();
        final Product product = ProductBuilderTest.create(
                category.getId(), brand.getId(), color.getId(), ProductStatus.ACTIVE
        );

        Mockito.when(this.categoryRepository.findById(Mockito.any())).thenReturn(Optional.of(category));

        Mockito.when(this.brandRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final DimensionComplementInput dimensionComplementInput = DimensionComplementInput.with(
                product.getHeight(),
                product.getWidth(),
                product.getLength()
        );

        final RegisterProductUseCaseInput registerProductUseCaseInput = RegisterProductUseCaseInput.with(
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
                this.registerProductUseCase.execute(registerProductUseCaseInput)
        );

        final String expectedErrorMessage = "Brand not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.categoryRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.brandRepository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when color does not exist")
    public void should_throw_NotFoundException_when_color_does_not_exist() {
        final Category category = CategoryBuilderTest.create();
        final Brand brand = BrandBuilderTest.create();
        final Color color = ColorBuilderTest.create();
        final Product product = ProductBuilderTest.create(
                category.getId(), brand.getId(), color.getId(), ProductStatus.ACTIVE
        );

        Mockito.when(this.categoryRepository.findById(Mockito.any())).thenReturn(Optional.of(category));

        Mockito.when(this.brandRepository.findById(Mockito.any())).thenReturn(Optional.of(brand));

        Mockito.when(this.colorRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final DimensionComplementInput dimensionComplementInput = DimensionComplementInput.with(
                product.getHeight(),
                product.getWidth(),
                product.getLength()
        );

        final RegisterProductUseCaseInput registerProductUseCaseInput = RegisterProductUseCaseInput.with(
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
                this.registerProductUseCase.execute(registerProductUseCaseInput)
        );

        final String expectedErrorMessage = "Color not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.categoryRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.brandRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.colorRepository, Mockito.times(1)).findById(Mockito.any());
    }
}
