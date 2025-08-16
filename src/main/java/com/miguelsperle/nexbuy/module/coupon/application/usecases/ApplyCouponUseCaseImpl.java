package com.miguelsperle.nexbuy.module.coupon.application.usecases;

import com.miguelsperle.nexbuy.module.coupon.application.ports.in.usecases.ApplyCouponUseCase;
import com.miguelsperle.nexbuy.module.coupon.application.ports.out.persistence.CouponRepository;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.inputs.ApplyCouponUseCaseInput;
import com.miguelsperle.nexbuy.module.coupon.domain.entities.Coupon;
import com.miguelsperle.nexbuy.module.coupon.domain.enums.CouponType;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

import java.time.LocalDateTime;
import java.util.Objects;

public class ApplyCouponUseCaseImpl implements ApplyCouponUseCase {
    private final CouponRepository couponRepository;

    public ApplyCouponUseCaseImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public void execute(ApplyCouponUseCaseInput applyCouponUseCaseInput) {
        final Coupon coupon = this.getCouponByCode(applyCouponUseCaseInput.code());

        if (!coupon.getIsActive()) {
            throw DomainException.with("Coupon is deactivated", 409);
        }

        if (TimeUtils.isExpired(coupon.getExpiresIn(), LocalDateTime.now())) {
            throw DomainException.with("Coupon has expired", 410);
        }

        if (coupon.getCouponType() == CouponType.LIMITED && coupon.getUsageLimit() != null && Objects.equals(coupon.getTimesUsed(), coupon.getUsageLimit())) {
            throw DomainException.with("Coupon has reached its usage limit", 409);
        }

        if (coupon.getMinimumPurchaseAmount().compareTo(applyCouponUseCaseInput.totalAmount()) > 0) {
            throw DomainException.with("Purchase amount is below the minimum required for this coupon", 422);
        }


    }

    private Coupon getCouponByCode(String code) {
        return this.couponRepository.findByCode(code)
                .orElseThrow(() -> NotFoundException.with("Coupon not found"));
    }
}
