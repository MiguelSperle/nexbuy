package com.miguelsperle.nexbuy.core.domain.utils;

import java.util.UUID;

public class IdentifierUtils {
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
