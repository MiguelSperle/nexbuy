package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.RegisterProductUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.exceptions.BrandNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.exceptions.CategoryNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.exceptions.ColorNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IRegisterProductUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IBrandGateway;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.ICategoryGateway;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IColorGateway;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IProductGateway;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.providers.ISkuProvider;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;
import com.miguelsperle.nexbuy.module.product.domain.entities.Color;
import com.miguelsperle.nexbuy.module.product.domain.entities.Product;

public class RegisterProductUseCase implements IRegisterProductUseCase {
    private final IProductGateway productGateway;
    private final ICategoryGateway categoryGateway;
    private final IBrandGateway brandGateway;
    private final IColorGateway colorGateway;
    private final ISkuProvider skuProvider;

    public RegisterProductUseCase(
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
    public void execute(RegisterProductUseCaseInput registerProductUseCaseInput) {
        final Category category = this.getCategoryById(registerProductUseCaseInput.categoryId());

        final Brand brand = this.getBrandById(registerProductUseCaseInput.brandId());

        final Color color = this.getColorById(registerProductUseCaseInput.colorId());

        final String skuGenerated = this.skuProvider.generateSku(
                registerProductUseCaseInput.name(),
                category.getName(),
                brand.getName(),
                color.getName()
        );

        final Product newProduct = Product.newProduct(
                registerProductUseCaseInput.name(),
                registerProductUseCaseInput.description(),
                category.getId(),
                registerProductUseCaseInput.price(),
                skuGenerated,
                brand.getId(),
                color.getId(),
                registerProductUseCaseInput.weight(),
                registerProductUseCaseInput.dimensionComplementInput().height(),
                registerProductUseCaseInput.dimensionComplementInput().width(),
                registerProductUseCaseInput.dimensionComplementInput().length()
        );

        this.saveProduct(newProduct);
    }

    private Category getCategoryById(String categoryId) {
        return this.categoryGateway.findById(categoryId)
                .orElseThrow(() -> CategoryNotFoundException.with("Category not found"));
    }

    private Brand getBrandById(String brandId) {
        return this.brandGateway.findById(brandId)
                .orElseThrow(() -> BrandNotFoundException.with("Brand not found"));
    }

    private Color getColorById(String colorId) {
        return this.colorGateway.findById(colorId)
                .orElseThrow(() -> ColorNotFoundException.with("Color not found"));
    }

    private void saveProduct(Product product) {
        this.productGateway.save(product);
    }
}
