package com.miguelsperle.nexbuy.module.coupon.domain.exceptions;

public class CouponAlreadyActivatedException extends RuntimeException {
    public CouponAlreadyActivatedException(String message) {
        super(message);
    }

    public static CouponAlreadyActivatedException with(String message) {
        return new CouponAlreadyActivatedException(message);
    }
}
