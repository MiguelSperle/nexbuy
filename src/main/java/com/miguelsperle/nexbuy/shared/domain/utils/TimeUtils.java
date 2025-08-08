package com.miguelsperle.nexbuy.shared.domain.utils;

import java.time.LocalDateTime;

public class TimeUtils {
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    public static boolean isExpired(LocalDateTime expirationTime, LocalDateTime now) {
        return now.isAfter(expirationTime);
    }
}
