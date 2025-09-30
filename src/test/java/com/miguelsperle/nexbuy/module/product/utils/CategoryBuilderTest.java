package com.miguelsperle.nexbuy.module.product.utils;

import com.miguelsperle.nexbuy.module.product.domain.entities.Category;
import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

public class CategoryBuilderTest {
    public static Category create() {
        return Category.with(
                IdentifierUtils.generateUUID(),
                "test-category",
                TimeUtils.now()
        );
    }
}
