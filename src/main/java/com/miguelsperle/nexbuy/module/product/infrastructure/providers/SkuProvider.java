package com.miguelsperle.nexbuy.module.product.infrastructure.providers;

import com.miguelsperle.nexbuy.module.product.domain.abstractions.providers.ISkuProvider;
import org.springframework.stereotype.Component;

@Component
public class SkuProvider implements ISkuProvider {
    @Override
    public String generateSku(String productCategory, String productBrand, String productModel, String productColor) {
        return "";
    }
}
