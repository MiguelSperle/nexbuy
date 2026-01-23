package com.miguelsperle.nexbuy.module.product.utils;

import com.miguelsperle.nexbuy.module.product.domain.entities.Color;
import com.miguelsperle.nexbuy.common.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.common.domain.utils.TimeUtils;

public class ColorBuilderTest {
    public static Color create() {
        return Color.with(
                IdentifierUtils.generateUUID(),
                "test-color",
                TimeUtils.now()
        );
    }
}
