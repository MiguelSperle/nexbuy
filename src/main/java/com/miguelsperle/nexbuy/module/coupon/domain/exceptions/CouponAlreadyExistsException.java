package com.miguelsperle.nexbuy.module.coupon.domain.exceptions;

public class CouponAlreadyExistsException extends RuntimeException {
    public CouponAlreadyExistsException(String message) {
        super(message);
    }

    public static CouponAlreadyExistsException with(String message) {
        return new CouponAlreadyExistsException(message);
    }
}
