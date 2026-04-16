package com.miguelsperle.nexbuy.module.product.application.abstractions.providers;

public interface SkuProvider {
    String generateSku(String productName, String categoryName, String brandName, String colorName);
}
