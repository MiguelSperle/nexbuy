package com.miguelsperle.nexbuy.module.product.domain.abstractions.providers;

public interface ISkuProvider {
    String generateSku(String productName, String categoryName, String brandName, String colorName);
}
