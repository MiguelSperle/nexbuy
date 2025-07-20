package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.UpdateProductUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.exceptions.*;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IUpdateProductUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IBrandGateway;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.ICategoryGateway;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IColorGateway;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IProductGateway;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.providers.ISkuProvider;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;
import com.miguelsperle.nexbuy.module.product.domain.entities.Color;
import com.miguelsperle.nexbuy.module.product.domain.entities.Product;
import com.miguelsperle.nexbuy.module.product.domain.enums.ProductStatus;

import java.util.Objects;

public class UpdateProductUseCase implements IUpdateProductUseCase {
    private final IProductGateway productGateway;
    private final ICategoryGateway categoryGateway;
    private final IBrandGateway brandGateway;
    private final IColorGateway colorGateway;
    private final ISkuProvider skuProvider;

    public UpdateProductUseCase(
            IProductGateway productGateway,
            ICategoryGateway categoryGateway,
            IBrandGateway brandGateway,
            IColorGateway colorGateway,
            ISkuProvider skuProvider
    ) {
        this.productGateway = productGateway;
        this.categoryGateway = categoryGateway;
        this.brandGateway = brandGateway;
        this.colorGateway = colorGateway;
        this.skuProvider = skuProvider;
    }

    @Override
    public void execute(UpdateProductUseCaseInput updateProductUseCaseInput) {
        final Product product = this.getProductById(updateProductUseCaseInput.productId());

        if (product.getProductStatus() == ProductStatus.DELETED) {
            throw new ProductAlreadyDeletedException("This product has already been deleted and cannot be updated");
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
        return this.productGateway.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    private Category getCategoryById(String categoryId) {
        return this.categoryGateway.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }

    private Brand getBrandById(String brandId) {
        return this.brandGateway.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException("Brand not found"));
    }

    private Color getColorById(String colorId) {
        return this.colorGateway.findById(colorId)
                .orElseThrow(() -> new ColorNotFoundException("Color not found"));
    }

    private void saveProduct(Product product) {
        this.productGateway.save(product);
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
