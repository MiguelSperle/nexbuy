package com.miguelsperle.nexbuy.module.coupon.application.usecases.io.inputs;

import com.miguelsperle.nexbuy.common.domain.pagination.SearchQuery;

public record GetCouponsUseCaseInput(
        SearchQuery searchQuery
) {
    public static GetCouponsUseCaseInput with(SearchQuery searchQuery) {
        return new GetCouponsUseCaseInput(searchQuery);
    }
}
