package com.miguelsperle.nexbuy.module.coupon.domain.exceptions;

public class CouponDeactivatedException extends RuntimeException {
    public CouponDeactivatedException(String message) {
        super(message);
    }

    public static CouponDeactivatedException with(String message) {
        return new CouponDeactivatedException(message);
    }
}
