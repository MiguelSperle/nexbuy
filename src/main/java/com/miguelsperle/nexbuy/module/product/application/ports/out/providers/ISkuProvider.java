package com.miguelsperle.nexbuy.module.product.application.ports.out.providers;

public interface ISkuProvider {
    String generateSku(String productName, String categoryName, String brandName, String colorName);
}
