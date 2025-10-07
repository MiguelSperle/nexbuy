package com.miguelsperle.nexbuy.module.coupon.application.usecases;

import com.miguelsperle.nexbuy.module.coupon.application.ports.out.persistence.CouponRepository;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.inputs.ApplyCouponUseCaseInput;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.outputs.ApplyCouponUseCaseOutput;
import com.miguelsperle.nexbuy.module.coupon.domain.entities.Coupon;
import com.miguelsperle.nexbuy.module.coupon.utils.CouponBuilderTest;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;
import com.miguelsperle.nexbuy.shared.domain.utils.DecimalUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ApplyCouponUseCaseTest {
    @InjectMocks
    private ApplyCouponUseCaseImpl applyCouponUseCase;

    @Mock
    private CouponRepository couponRepository;

    @Test
    @DisplayName("Should apply coupon")
    public void should_apply_coupon() {
        final Coupon coupon = CouponBuilderTest.create().withIsActive(true);

        Mockito.when(this.couponRepository.findByCode(Mockito.any())).thenReturn(Optional.of(coupon));

        final ApplyCouponUseCaseInput applyCouponUseCaseInput = ApplyCouponUseCaseInput.with(
                coupon.getCode(),
                DecimalUtils.valueOf(100)
        );

        final ApplyCouponUseCaseOutput applyCouponUseCaseOutput = this.applyCouponUseCase.execute(applyCouponUseCaseInput);

        Assertions.assertNotNull(applyCouponUseCaseOutput);
        Assertions.assertNotNull(applyCouponUseCaseOutput.discountPercentage());
        Assertions.assertNotNull(applyCouponUseCaseOutput.totalAmountWithDiscount());

        Assertions.assertEquals(coupon.getPercentage(), applyCouponUseCaseOutput.discountPercentage());

        Mockito.verify(this.couponRepository, Mockito.times(1)).findByCode(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when coupon does not exist")
    public void should_throw_NotFoundException_when_coupon_does_not_exist() {
        final Coupon coupon = CouponBuilderTest.create();

        Mockito.when(this.couponRepository.findByCode(Mockito.any())).thenReturn(Optional.empty());

        final ApplyCouponUseCaseInput applyCouponUseCaseInput = ApplyCouponUseCaseInput.with(
                coupon.getCode(),
                DecimalUtils.valueOf(100)
        );

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.applyCouponUseCase.execute(applyCouponUseCaseInput)
        );

        final String expectedErrorMessage = "Coupon not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.couponRepository, Mockito.times(1)).findByCode(Mockito.any());
    }

    @Test
    @DisplayName("Should throw DomainException when coupon is not active")
    public void should_throw_DomainException_when_coupon_is_not_active() {
        final Coupon coupon = CouponBuilderTest.create();

        Mockito.when(this.couponRepository.findByCode(Mockito.any())).thenReturn(Optional.of(coupon));

        final ApplyCouponUseCaseInput applyCouponUseCaseInput = ApplyCouponUseCaseInput.with(
                coupon.getCode(),
                DecimalUtils.valueOf(100)
        );

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.applyCouponUseCase.execute(applyCouponUseCaseInput)
        );

        final String expectedErrorMessage = "Coupon is deactivated";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.couponRepository, Mockito.times(1)).findByCode(Mockito.any());
    }

    @Test
    @DisplayName("Should throw DomainException when coupon is expired")
    public void should_throw_DomainException_when_coupon_is_expired() {
        final Coupon coupon = CouponBuilderTest.create().withIsActive(true).withExpiresIn(TimeUtils.now().minusDays(1));

        Mockito.when(this.couponRepository.findByCode(Mockito.any())).thenReturn(Optional.of(coupon));

        final ApplyCouponUseCaseInput applyCouponUseCaseInput = ApplyCouponUseCaseInput.with(
                coupon.getCode(),
                DecimalUtils.valueOf(100)
        );

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.applyCouponUseCase.execute(applyCouponUseCaseInput)
        );

        final String expectedErrorMessage = "Coupon has expired";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.couponRepository, Mockito.times(1)).findByCode(Mockito.any());
    }

    @Test
    @DisplayName("Should throw DomainException when purchase amount is below minimum required")
    public void should_throw_DomainException_when_purchase_amount_is_below_minimum_required() {
        final Coupon coupon = CouponBuilderTest.create().withIsActive(true);

        Mockito.when(this.couponRepository.findByCode(Mockito.any())).thenReturn(Optional.of(coupon));

        final ApplyCouponUseCaseInput applyCouponUseCaseInput = ApplyCouponUseCaseInput.with(
                coupon.getCode(),
                DecimalUtils.valueOf(99)
        );

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.applyCouponUseCase.execute(applyCouponUseCaseInput)
        );

        final String expectedErrorMessage = "The purchase amount is below the minimum required to user this coupon";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.couponRepository, Mockito.times(1)).findByCode(Mockito.any());
    }
}
