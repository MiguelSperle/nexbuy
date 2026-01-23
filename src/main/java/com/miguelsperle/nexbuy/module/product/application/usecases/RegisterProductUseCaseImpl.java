package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.shared.application.ports.out.producer.MessageProducer;
import com.miguelsperle.nexbuy.shared.domain.events.ProductRegisteredEvent;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.RegisterProductUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.ports.in.usecases.RegisterProductUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.BrandRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.CategoryRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ColorRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ProductRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.providers.SkuProvider;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;
import com.miguelsperle.nexbuy.module.product.domain.entities.Color;
import com.miguelsperle.nexbuy.module.product.domain.entities.Product;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

public class RegisterProductUseCaseImpl implements RegisterProductUseCase {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ColorRepository colorRepository;
    private final SkuProvider skuProvider;
    private final MessageProducer messageProducer;

    private static final String PRODUCT_REGISTERED_EXCHANGE = "product.registered.exchange";
    private static final String PRODUCT_REGISTERED_ROUTING_KEY = "product.registered.routing.key";

    public RegisterProductUseCaseImpl(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            BrandRepository brandRepository,
            ColorRepository colorRepository,
            SkuProvider skuProvider,
            MessageProducer messageProducer
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.colorRepository = colorRepository;
        this.skuProvider = skuProvider;
        this.messageProducer = messageProducer;
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

        final Product savedProduct = this.saveProduct(newProduct);

        final ProductRegisteredEvent productRegisteredEvent = ProductRegisteredEvent.from(
                savedProduct.getId(),
                savedProduct.getSku()
        );

        this.messageProducer.publish(PRODUCT_REGISTERED_EXCHANGE, PRODUCT_REGISTERED_ROUTING_KEY, productRegisteredEvent);
    }

    private Category getCategoryById(String categoryId) {
        return this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> NotFoundException.with("Category not found"));
    }

    private Brand getBrandById(String brandId) {
        return this.brandRepository.findById(brandId)
                .orElseThrow(() -> NotFoundException.with("Brand not found"));
    }

    private Color getColorById(String colorId) {
        return this.colorRepository.findById(colorId)
                .orElseThrow(() -> NotFoundException.with("Color not found"));
    }

    private Product saveProduct(Product product) {
        return this.productRepository.save(product);
    }
}
