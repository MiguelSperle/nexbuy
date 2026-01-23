package com.miguelsperle.nexbuy.shared.domain.utils;

import java.math.BigDecimal;

public class DecimalUtils {
    public static BigDecimal valueOf(int value) {
        return new BigDecimal(value);
    }

    public static BigDecimal valueOf(String value) {
        return new BigDecimal(value);
    }
}
