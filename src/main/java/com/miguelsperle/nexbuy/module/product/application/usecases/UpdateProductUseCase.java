package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.UpdateProductUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.ports.in.IUpdateProductUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.IBrandRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ICategoryRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.IColorRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.IProductRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.providers.ISkuProvider;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;
import com.miguelsperle.nexbuy.module.product.domain.entities.Color;
import com.miguelsperle.nexbuy.module.product.domain.entities.Product;
import com.miguelsperle.nexbuy.module.product.domain.enums.ProductStatus;
import com.miguelsperle.nexbuy.module.product.domain.exceptions.*;

import java.util.Objects;

public class UpdateProductUseCase implements IUpdateProductUseCase {
    private final IProductRepository productRepository;
    private final ICategoryRepository categoryRepository;
    private final IBrandRepository brandRepository;
    private final IColorRepository colorRepository;
    private final ISkuProvider skuProvider;

    public UpdateProductUseCase(
            IProductRepository productRepository,
            ICategoryRepository categoryRepository,
            IBrandRepository brandRepository,
            IColorRepository colorRepository,
            ISkuProvider skuProvider
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.colorRepository = colorRepository;
        this.skuProvider = skuProvider;
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

        final String sku = this.verifySkuUpdateRequired(
                updateProductUseCaseInput.name(),
                category,
                brand,
                color,
                product
        ) ? this.skuProvider.generateSku(updateProductUseCaseInput.name(), category.getName(), brand.getName(), color.getName())
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

        this.saveProduct(updatedProduct);
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

    private void saveProduct(Product product) {
        this.productRepository.save(product);
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
