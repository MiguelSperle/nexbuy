package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.core.application.ports.out.transaction.ITransactionExecutor;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.RegisterProductUseCaseInput;
import com.miguelsperle.nexbuy.module.product.domain.exceptions.BrandNotFoundException;
import com.miguelsperle.nexbuy.module.product.domain.exceptions.CategoryNotFoundException;
import com.miguelsperle.nexbuy.module.product.domain.exceptions.ColorNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.ports.in.IRegisterProductUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.IBrandRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ICategoryRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.IColorRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.IProductRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.providers.ISkuProvider;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;
import com.miguelsperle.nexbuy.module.product.domain.entities.Color;
import com.miguelsperle.nexbuy.module.product.domain.entities.Product;
import com.miguelsperle.nexbuy.module.inventory.application.ports.in.ICreateInventoryUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.CreateInventoryUseCaseInput;

public class RegisterProductUseCase implements IRegisterProductUseCase {
    private final IProductRepository productRepository;
    private final ICategoryRepository categoryRepository;
    private final IBrandRepository brandRepository;
    private final IColorRepository colorRepository;
    private final ISkuProvider skuProvider;
    private final ICreateInventoryUseCase createInventoryUseCase;
    private final ITransactionExecutor transactionExecutor;

    public RegisterProductUseCase(
            IProductRepository productRepository,
            ICategoryRepository categoryRepository,
            IBrandRepository brandRepository,
            IColorRepository colorRepository,
            ISkuProvider skuProvider,
            ICreateInventoryUseCase createInventoryUseCase,
            ITransactionExecutor transactionExecutor
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.colorRepository = colorRepository;
        this.skuProvider = skuProvider;
        this.createInventoryUseCase = createInventoryUseCase;
        this.transactionExecutor = transactionExecutor;
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

        this.transactionExecutor.runTransaction(() -> {
            final Product savedProduct = this.saveProduct(newProduct);

            this.createInventoryUseCase.execute(CreateInventoryUseCaseInput.with(
                    savedProduct.getId(),
                    savedProduct.getSku()
            ));
        });
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
}
