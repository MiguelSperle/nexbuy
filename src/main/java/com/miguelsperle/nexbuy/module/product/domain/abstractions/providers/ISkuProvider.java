package com.miguelsperle.nexbuy.module.product.domain.abstractions.providers;

public interface ISkuProvider {
    String generateSku(String productCategory, String productBrand, String productModel, String productColor);
}
