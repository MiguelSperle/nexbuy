package com.miguelsperle.nexbuy.module.coupon.infrastructure.adapters.in.web.dtos.responses;

import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;

public record GetCouponsResponse() {
    public static Pagination<GetCouponsResponse> from() {
        return null;
    }
}
