package com.miguelsperle.nexbuy.module.product.infrastructure.providers;

import com.miguelsperle.nexbuy.module.product.domain.abstractions.providers.ISkuProvider;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.regex.Pattern;

@Component
public class SkuProvider implements ISkuProvider {
    private static final String REMOVE_WHITE_SPACES = Pattern.compile("\\s+").pattern();
    private static final Pattern CHECK_CONTAINS_NUMBERS = Pattern.compile(".*\\d+.*");
    private static final String DEFAULT_SKU_VALUE = "XXXX";
    private static final int MAX_WORD_LENGTH = 4;

    @Override
    public String generateSku(String productName, String categoryName, String brandName, String colorName) {
        final String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        final String processedName = this.processName(productName);
        final String processedCategoryName = this.processCategory(categoryName);
        final String processedBrandName = this.processBrand(brandName);
        final String processedColorName = this.processColor(colorName);
        final String extractedNameCharacters = this.extractProductNameCharacters(processedName);

        return String.format("%s-%s-%s-%s-%s", uniqueId, extractedNameCharacters, processedCategoryName, processedBrandName, processedColorName);
    }

    private String processName(String productName) {
        return (productName == null || productName.isBlank()) ? DEFAULT_SKU_VALUE : productName.toUpperCase();
    }

    private String processCategory(String categoryName) {
        return (categoryName == null || categoryName.isBlank()) ? DEFAULT_SKU_VALUE :
                categoryName.trim().replaceAll(REMOVE_WHITE_SPACES, "").toUpperCase();
    }

    private String processBrand(String brandName) {
        return (brandName == null || brandName.isBlank()) ? DEFAULT_SKU_VALUE :
                brandName.trim().replaceAll(REMOVE_WHITE_SPACES, "").toUpperCase();
    }

    private String processColor(String colorName) {
        return (colorName == null || colorName.isBlank()) ? DEFAULT_SKU_VALUE :
                colorName.trim().replaceAll(REMOVE_WHITE_SPACES, "").toUpperCase();
    }

    private String extractProductNameCharacters(String processedName) {
        final StringBuilder builder = new StringBuilder();
        final String[] words = processedName.split(REMOVE_WHITE_SPACES);

        final boolean containsNumbers = CHECK_CONTAINS_NUMBERS.matcher(processedName).matches();
        final boolean singleWord = words.length < 2;

        for (String word : words) {
            if (word.length() < MAX_WORD_LENGTH || singleWord || containsNumbers) {
                builder.append(word, 0, Math.min(MAX_WORD_LENGTH, word.length()));
            } else {
                builder.append(word.charAt(0));
            }
        }

        return builder.toString().toUpperCase();
    }
}
