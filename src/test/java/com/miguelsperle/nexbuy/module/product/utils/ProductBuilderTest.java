package com.miguelsperle.nexbuy.module.product.utils;

import com.miguelsperle.nexbuy.module.product.domain.entities.Product;
import com.miguelsperle.nexbuy.module.product.domain.enums.ProductStatus;
import com.miguelsperle.nexbuy.shared.domain.utils.DecimalUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

public class ProductBuilderTest {
    public static Product create(String categoryId, String brandId, String colorId, ProductStatus productStatus) {
        return Product.with(
                IdentifierUtils.generateUUID(),
                "test-name",
                "test-description",
                categoryId,
                DecimalUtils.valueOf(50),
                "test-sku",
                brandId,
                colorId,
                productStatus,
                10,
                100,
                100,
                100,
                TimeUtils.now()
        );
    }
}
