package com.miguelsperle.nexbuy.module.coupon.application.usecases;

import com.miguelsperle.nexbuy.module.coupon.application.ports.in.usecases.ApplyCouponUseCase;
import com.miguelsperle.nexbuy.module.coupon.application.ports.out.persistence.CouponRepository;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.inputs.ApplyCouponUseCaseInput;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.outputs.ApplyCouponUseCaseOutput;
import com.miguelsperle.nexbuy.module.coupon.domain.entities.Coupon;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public class ApplyCouponUseCaseImpl implements ApplyCouponUseCase {
    private final CouponRepository couponRepository;

    public ApplyCouponUseCaseImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public ApplyCouponUseCaseOutput execute(ApplyCouponUseCaseInput applyCouponUseCaseInput) {
        final Coupon coupon = this.getCouponByCode(applyCouponUseCaseInput.code());

        if (!coupon.getIsActive()) {
            throw DomainException.with("Coupon is deactivated", 409);
        }

        if (TimeUtils.isExpired(coupon.getExpiresIn(), LocalDateTime.now())) {
            throw DomainException.with("Coupon has expired", 410);
        }

        if (coupon.getMinimumPurchaseAmount().compareTo(applyCouponUseCaseInput.totalAmount()) > 0) {
            throw DomainException.with("The purchase amount is below the minimum required to user this coupon", 422);
        }

        final BigDecimal totalAmount = applyCouponUseCaseInput.totalAmount();
        final Integer percentage = coupon.getPercentage();

        final BigDecimal discountAmount = totalAmount.multiply(BigDecimal.valueOf(percentage)).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        final BigDecimal finalAmount = totalAmount.subtract(discountAmount);

        return ApplyCouponUseCaseOutput.from(totalAmount, percentage, finalAmount);
    }

    private Coupon getCouponByCode(String code) {
        return this.couponRepository.findByCode(code)
                .orElseThrow(() -> NotFoundException.with("Coupon not found"));
    }
}
