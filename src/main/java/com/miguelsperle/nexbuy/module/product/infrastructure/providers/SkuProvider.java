package com.miguelsperle.nexbuy.module.product.infrastructure.providers;

import com.miguelsperle.nexbuy.module.product.domain.abstractions.providers.ISkuProvider;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.regex.Pattern;

@Component
public class SkuProvider implements ISkuProvider {
    private static final Pattern CHECK_CONTAINS_NUMBERS = Pattern.compile(".*\\d+.*");
    private static final String REMOVE_WHITE_SPACES = Pattern.compile("\\s+").pattern();
    private static final String DEFAULT_SKU_VALUE = "XXXX";
    private static final int MAX_WORD_LENGTH = 4;

    @Override
    public String generateSku(String productName, String categoryName, String brandName, String colorName) {
        final String compressedProductName = this.compressProductName(productName);
        final String abbreviatedCategoryName = this.abbreviate(categoryName);
        final String abbreviatedBrandName = this.abbreviate(brandName);
        final String abbreviatedColorName = this.abbreviate(colorName);
        final String shortUUID = UUID.randomUUID().toString().substring(0, 5).toUpperCase();

        return String.format("%s-%s-%s-%s-%s", compressedProductName, abbreviatedCategoryName, abbreviatedBrandName, abbreviatedColorName, shortUUID);
    }

    private String abbreviate(String value) {
        if (value == null || value.isBlank()) return DEFAULT_SKU_VALUE;
        final String cleaned = value.trim().replaceAll(REMOVE_WHITE_SPACES, "").toUpperCase();
        return cleaned.length() <= 3 ? cleaned : cleaned.substring(0, 3);
    }

    private String compressProductName(String productName) {
        if (productName == null || productName.isBlank()) return DEFAULT_SKU_VALUE;

        final StringBuilder stringBuilder = new StringBuilder();
        final String[] words = productName.split(REMOVE_WHITE_SPACES);

        for (String word : words) {
            if (word.length() < MAX_WORD_LENGTH || words.length < 2 || CHECK_CONTAINS_NUMBERS.matcher(productName).matches()) {
                stringBuilder.append(word, 0, Math.min(MAX_WORD_LENGTH, word.length()));
            } else {
                stringBuilder.append(word.charAt(0));
            }
        }
        return stringBuilder.toString().toUpperCase();
    }
}
