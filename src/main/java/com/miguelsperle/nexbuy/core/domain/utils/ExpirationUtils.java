package com.miguelsperle.nexbuy.core.domain.utils;

import java.time.LocalDateTime;

public class ExpirationUtils {
    public static boolean isExpired(LocalDateTime expirationTime, LocalDateTime now) {
        return now.isAfter(expirationTime);
    }
}
