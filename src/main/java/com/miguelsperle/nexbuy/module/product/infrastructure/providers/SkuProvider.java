package com.miguelsperle.nexbuy.module.product.infrastructure.providers;

import com.miguelsperle.nexbuy.module.product.domain.abstractions.providers.ISkuProvider;
import org.springframework.stereotype.Component;

@Component
public class SkuProvider implements ISkuProvider {
    private static final String REMOVE_WHITE_SPACES = "\\s+";
    private static final String DEFAULT_SKU_VALUE = "XXXX";

    @Override
    public String generateSku(String productName, String categoryName, String brandName, String colorName) {
        final String processedProductName = this.abbreviate(productName, 4);
        final String processedCategoryName = this.abbreviate(categoryName, 3);
        final String processedBrandName = this.abbreviate(brandName, 3);
        final String processedColorName = this.abbreviate(colorName, 3);

        return String.format("%s-%s%s%s", processedProductName, processedCategoryName, processedBrandName, processedColorName);
    }

    private String abbreviate(String value, int length) {
        if (value == null || value.isBlank()) return DEFAULT_SKU_VALUE;
        final String cleaned = value.trim().replaceAll(REMOVE_WHITE_SPACES, "").toUpperCase();
        return cleaned.length() <= length ? cleaned : cleaned.substring(0, length);
    }
}
