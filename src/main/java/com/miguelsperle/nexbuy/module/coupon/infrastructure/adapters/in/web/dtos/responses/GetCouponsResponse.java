package com.miguelsperle.nexbuy.module.coupon.infrastructure.adapters.in.web.dtos.responses;

import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.outputs.GetCouponsUseCaseOutput;
import com.miguelsperle.nexbuy.module.coupon.domain.enums.CouponType;
import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record GetCouponsResponse(
        String id,
        String code,
        Integer percentage,
        BigDecimal minimumPurchaseAmount,
        CouponType type,
        Integer timesUsed,
        Integer usageLimit,
        Boolean isActive,
        LocalDateTime expiresIn
) {
    public static Pagination<GetCouponsResponse> from(GetCouponsUseCaseOutput getCouponsUseCaseOutput) {
        return getCouponsUseCaseOutput.paginatedCoupons().map(paginatedCoupon -> new GetCouponsResponse(
                paginatedCoupon.getId(),
                paginatedCoupon.getCode(),
                paginatedCoupon.getPercentage(),
                paginatedCoupon.getMinimumPurchaseAmount(),
                paginatedCoupon.getCouponType(),
                paginatedCoupon.getTimesUsed(),
                paginatedCoupon.getUsageLimit(),
                paginatedCoupon.getIsActive(),
                paginatedCoupon.getExpiresIn()
        ));
    }
}
