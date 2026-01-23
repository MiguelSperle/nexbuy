package com.miguelsperle.nexbuy.common.domain.utils;

import java.util.UUID;

public class IdentifierUtils {
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
