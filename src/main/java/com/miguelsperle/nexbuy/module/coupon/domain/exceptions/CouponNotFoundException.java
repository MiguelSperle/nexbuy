package com.miguelsperle.nexbuy.module.coupon.domain.exceptions;

public class CouponNotFoundException extends RuntimeException {
    public CouponNotFoundException(String message) {
        super(message);
    }

    public static CouponNotFoundException with(String message) {
        return new CouponNotFoundException(message);
    }
}
