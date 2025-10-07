package com.miguelsperle.nexbuy.module.coupon.application.usecases;

import com.miguelsperle.nexbuy.module.coupon.application.ports.out.persistence.CouponRepository;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.inputs.UpdateCouponUseCaseInput;
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
public class UpdateCouponUseCaseTest {
    @InjectMocks
    private UpdateCouponUseCaseImpl updateCouponUseCase;

    @Mock
    private CouponRepository couponRepository;

    @Test
    @DisplayName("Should update coupon")
    public void should_update_coupon() {
        final Coupon coupon = CouponBuilderTest.create(false);

        Mockito.when(this.couponRepository.findById(Mockito.any())).thenReturn(Optional.of(coupon));

        final UpdateCouponUseCaseInput updateCouponUseCaseInput = UpdateCouponUseCaseInput.with(
                coupon.getId(),
                coupon.getCode(),
                coupon.getPercentage(),
                coupon.getMinimumPurchaseAmount(),
                coupon.getExpiresIn()
        );

        final Coupon updatedCoupon = coupon.withCode(updateCouponUseCaseInput.code())
                .withPercentage(updateCouponUseCaseInput.percentage())
                .withMinimumPurchaseAmount(updateCouponUseCaseInput.minimumPurchaseAmount())
                .withExpiresIn(updateCouponUseCaseInput.expiresIn());

        Mockito.when(this.couponRepository.save(Mockito.any())).thenReturn(updatedCoupon);

        this.updateCouponUseCase.execute(updateCouponUseCaseInput);

        Mockito.verify(this.couponRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.couponRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when coupon does not exist")
    public void should_throw_NotFoundException_when_coupon_does_not_exist() {
        final Coupon coupon = CouponBuilderTest.create(false);

        Mockito.when(this.couponRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final UpdateCouponUseCaseInput updateCouponUseCaseInput = UpdateCouponUseCaseInput.with(
                coupon.getId(),
                coupon.getCode(),
                coupon.getPercentage(),
                coupon.getMinimumPurchaseAmount(),
                coupon.getExpiresIn()
        );

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.updateCouponUseCase.execute(updateCouponUseCaseInput)
        );

        final String expectedErrorMessage = "Coupon not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.couponRepository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    @DisplayName("Should throw DomainException when coupon with same code already exists")
    public void should_throw_DomainException_when_coupon_with_same_code_already_exists() {
        final Coupon coupon = CouponBuilderTest.create(false);
        final String newCode = "test-new-code";

        Mockito.when(this.couponRepository.findById(Mockito.any())).thenReturn(Optional.of(coupon));

        Mockito.when(this.couponRepository.existsByCode(Mockito.any())).thenReturn(true);

        final UpdateCouponUseCaseInput updateCouponUseCaseInput = UpdateCouponUseCaseInput.with(
                coupon.getId(),
                newCode,
                coupon.getPercentage(),
                coupon.getMinimumPurchaseAmount(),
                coupon.getExpiresIn()
        );

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.updateCouponUseCase.execute(updateCouponUseCaseInput)
        );

        final String expectedErrorMessage = "Coupon with this code already exists";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.couponRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.couponRepository, Mockito.times(1)).existsByCode(Mockito.any());
    }
}
