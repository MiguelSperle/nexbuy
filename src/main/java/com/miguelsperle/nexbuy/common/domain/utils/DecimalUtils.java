package com.miguelsperle.nexbuy.common.domain.utils;

import java.math.BigDecimal;

public class DecimalUtils {
    public static BigDecimal valueOf(int value) {
        return new BigDecimal(value);
    }

    public static BigDecimal valueOf(String value) {
        return new BigDecimal(value);
    }
}
