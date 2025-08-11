package com.miguelsperle.nexbuy.module.coupon.application.usecases;

import com.miguelsperle.nexbuy.module.coupon.application.ports.in.GetCouponsUseCase;
import com.miguelsperle.nexbuy.module.coupon.application.ports.out.persistence.CouponRepository;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.inputs.GetCouponsUseCaseInput;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.outputs.GetCouponsUseCaseOutput;
import com.miguelsperle.nexbuy.module.coupon.domain.entities.Coupon;
import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.shared.domain.pagination.SearchQuery;

public class GetCouponsUseCaseImpl implements GetCouponsUseCase {
    private final CouponRepository couponRepository;

    public GetCouponsUseCaseImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public GetCouponsUseCaseOutput execute(GetCouponsUseCaseInput getCouponsUseCaseInput) {
        final Pagination<Coupon> paginatedCoupons = this.getAllPaginatedCoupons(getCouponsUseCaseInput.searchQuery());

        return GetCouponsUseCaseOutput.from(paginatedCoupons);
    }

    private Pagination<Coupon> getAllPaginatedCoupons(SearchQuery searchQuery) {
        return this.couponRepository.findAllPaginated(searchQuery);
    }
}
