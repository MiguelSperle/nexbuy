package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.core.application.ports.out.providers.DomainEventPublisherProvider;
import com.miguelsperle.nexbuy.core.domain.events.ProductSkuUpdatedEvent;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.UpdateProductUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.ports.in.UpdateProductUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.BrandRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.CategoryRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ColorRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ProductRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.providers.SkuProvider;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;
import com.miguelsperle.nexbuy.module.product.domain.entities.Color;
import com.miguelsperle.nexbuy.module.product.domain.entities.Product;
import com.miguelsperle.nexbuy.module.product.domain.enums.ProductStatus;
import com.miguelsperle.nexbuy.module.product.domain.exceptions.*;

import java.util.Objects;

public class UpdateProductUseCaseImpl implements UpdateProductUseCase {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ColorRepository colorRepository;
    private final SkuProvider skuProvider;
    private final DomainEventPublisherProvider domainEventPublisherProvider;

    public UpdateProductUseCaseImpl(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            BrandRepository brandRepository,
            ColorRepository colorRepository,
            SkuProvider skuProvider,
            DomainEventPublisherProvider domainEventPublisherProvider
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.colorRepository = colorRepository;
        this.skuProvider = skuProvider;
        this.domainEventPublisherProvider = domainEventPublisherProvider;
    }

    @Override
    public void execute(UpdateProductUseCaseInput updateProductUseCaseInput) {
        final Product product = this.getProductById(updateProductUseCaseInput.productId());

        if (product.getProductStatus() == ProductStatus.DELETED) {
            throw ProductAlreadyDeletedException.with("This product has already been deleted and cannot be updated");
        }

        final Category category = this.getCategoryById(updateProductUseCaseInput.categoryId());

        final Brand brand = this.getBrandById(updateProductUseCaseInput.brandId());

        final Color color = this.getColorById(updateProductUseCaseInput.colorId());

        final boolean skuUpdateRequired = this.verifySkuUpdateRequired(updateProductUseCaseInput.name(), category, brand, color, product);

        final String sku = skuUpdateRequired
                ? this.skuProvider.generateSku(updateProductUseCaseInput.name(), category.getName(), brand.getName(), color.getName())
                : product.getSku();

        final Product updatedProduct = product.withName(updateProductUseCaseInput.name())
                .withDescription(updateProductUseCaseInput.description())
                .withCategory(category.getId())
                .withPrice(updateProductUseCaseInput.price())
                .withSku(sku)
                .withBrand(brand.getId())
                .withColor(color.getId())
                .withWeight(updateProductUseCaseInput.weight())
                .withHeight(updateProductUseCaseInput.dimensionComplementInput().height())
                .withWidth(updateProductUseCaseInput.dimensionComplementInput().width())
                .withLength(updateProductUseCaseInput.dimensionComplementInput().length());

        final Product savedProduct = this.saveProduct(updatedProduct);

        if (!Objects.equals(product.getSku(), savedProduct.getSku())) {
            this.domainEventPublisherProvider.publishEvent(ProductSkuUpdatedEvent.from(
                    savedProduct.getId(),
                    savedProduct.getSku()
            ));
        }
    }

    private Product getProductById(String productId) {
        return this.productRepository.findById(productId)
                .orElseThrow(() -> ProductNotFoundException.with("Product not found"));
    }

    private Category getCategoryById(String categoryId) {
        return this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> CategoryNotFoundException.with("Category not found"));
    }

    private Brand getBrandById(String brandId) {
        return this.brandRepository.findById(brandId)
                .orElseThrow(() -> BrandNotFoundException.with("Brand not found"));
    }

    private Color getColorById(String colorId) {
        return this.colorRepository.findById(colorId)
                .orElseThrow(() -> ColorNotFoundException.with("Color not found"));
    }

    private Product saveProduct(Product product) {
        return this.productRepository.save(product);
    }

    private boolean verifySkuUpdateRequired(
            String productName,
            Category category,
            Brand brand,
            Color color,
            Product product
    ) {
        return !Objects.equals(productName, product.getName()) ||
                !Objects.equals(category.getId(), product.getCategoryId()) ||
                !Objects.equals(brand.getId(), product.getBrandId()) ||
                !Objects.equals(color.getId(), product.getColorId());
    }
}
