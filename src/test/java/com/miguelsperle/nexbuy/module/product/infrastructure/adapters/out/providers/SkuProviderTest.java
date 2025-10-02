package com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.providers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SkuProviderTest {
    private SkuProviderImpl skuProvider;

    @BeforeEach
    public void setUp() {
        skuProvider = new SkuProviderImpl();
    }

    @Test
    @DisplayName("Should generate sku")
    public void should_generate_sku() {
        String productName = "Smartphone Galaxy";
        String categoryName = "Electronics";
        String brandName = "Samsung";
        String colorName = "Black";

        final String skuGenerated = this.skuProvider.generateSku(
                productName, categoryName, brandName, colorName
        );

        final String[] parts = skuGenerated.split("-");

        Assertions.assertNotNull(skuGenerated);
        Assertions.assertEquals(5, parts.length);
    }
}
