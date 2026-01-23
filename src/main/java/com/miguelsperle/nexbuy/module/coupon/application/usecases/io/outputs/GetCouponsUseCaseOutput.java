package com.miguelsperle.nexbuy.module.coupon.application.usecases.io.outputs;

import com.miguelsperle.nexbuy.module.coupon.domain.entities.Coupon;
import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;

public record GetCouponsUseCaseOutput(
        Pagination<Coupon> paginatedCoupons
) {
    public static GetCouponsUseCaseOutput from(Pagination<Coupon> paginatedCoupons) {
        return new GetCouponsUseCaseOutput(paginatedCoupons);
    }
}
