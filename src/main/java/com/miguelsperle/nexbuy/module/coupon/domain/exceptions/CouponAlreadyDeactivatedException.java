package com.miguelsperle.nexbuy.module.coupon.domain.exceptions;

public class CouponAlreadyDeactivatedException extends RuntimeException {
    public CouponAlreadyDeactivatedException(String message) {
        super(message);
    }

    public static CouponAlreadyDeactivatedException with(String message) {
        return new CouponAlreadyDeactivatedException(message);
    }
}
