package com.miguelsperle.nexbuy.module.product.utils;

import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;
import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

public class BrandBuilderTest {
    public static Brand create() {
        return Brand.with(
                IdentifierUtils.generateUUID(),
                "BWayne",
                TimeUtils.now()
        );
    }
}
