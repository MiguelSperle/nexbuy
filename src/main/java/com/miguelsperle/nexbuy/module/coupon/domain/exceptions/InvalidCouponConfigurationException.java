package com.miguelsperle.nexbuy.module.coupon.domain.exceptions;

public class InvalidCouponConfigurationException extends RuntimeException {
    public InvalidCouponConfigurationException(String message) {
        super(message);
    }

    public static InvalidCouponConfigurationException with(String message) {
        return new InvalidCouponConfigurationException(message);
    }
}
