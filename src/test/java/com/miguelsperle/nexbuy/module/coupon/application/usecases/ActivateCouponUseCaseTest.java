package com.miguelsperle.nexbuy.module.coupon.application.usecases;

import com.miguelsperle.nexbuy.module.coupon.application.ports.out.persistence.CouponRepository;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.inputs.ActivateCouponUseCaseInput;
import com.miguelsperle.nexbuy.module.coupon.domain.entities.Coupon;
import com.miguelsperle.nexbuy.module.coupon.utils.CouponBuilderTest;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;
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
public class ActivateCouponUseCaseTest {
    @InjectMocks
    private ActivateCouponUseCaseImpl activateCouponUseCase;

    @Mock
    private CouponRepository couponRepository;

    @Test
    @DisplayName("Should activate coupon")
    public void should_activate_coupon() {
        final Coupon coupon = CouponBuilderTest.create(false);

        Mockito.when(this.couponRepository.findById(Mockito.any())).thenReturn(Optional.of(coupon));

        final Coupon updatedCoupon = coupon.withIsActive(true);

        Mockito.when(this.couponRepository.save(Mockito.any())).thenReturn(updatedCoupon);

        final ActivateCouponUseCaseInput activateCouponUseCaseInput = ActivateCouponUseCaseInput.with(
                coupon.getId()
        );

        this.activateCouponUseCase.execute(activateCouponUseCaseInput);

        Mockito.verify(this.couponRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.couponRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when coupon does not exist")
    public void should_throw_NotFoundException_when_coupon_does_not_exist() {
        final Coupon coupon = CouponBuilderTest.create(false);

        Mockito.when(this.couponRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final ActivateCouponUseCaseInput activateCouponUseCaseInput = ActivateCouponUseCaseInput.with(
                coupon.getId()
        );

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.activateCouponUseCase.execute(activateCouponUseCaseInput)
        );

        final String expectedErrorMessage = "Coupon not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.couponRepository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    @DisplayName("Should throw DomainException when coupon is already activated")
    public void should_throw_DomainException_when_coupon_is_already_activated() {
        final Coupon coupon = CouponBuilderTest.create(true);

        Mockito.when(this.couponRepository.findById(Mockito.any())).thenReturn(Optional.of(coupon));

        final ActivateCouponUseCaseInput activateCouponUseCaseInput = ActivateCouponUseCaseInput.with(
                coupon.getId()
        );

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.activateCouponUseCase.execute(activateCouponUseCaseInput)
        );

        final String expectedErrorMessage = "Coupon is already activated";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.couponRepository, Mockito.times(1)).findById(Mockito.any());
    }
}
