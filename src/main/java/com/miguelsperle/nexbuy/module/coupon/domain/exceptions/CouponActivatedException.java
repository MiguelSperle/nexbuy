package com.miguelsperle.nexbuy.module.coupon.domain.exceptions;

public class CouponActivatedException extends RuntimeException {
    public CouponActivatedException(String message) {
        super(message);
    }

    public static CouponActivatedException with(String message) {
        return new CouponActivatedException(message);
    }
}
