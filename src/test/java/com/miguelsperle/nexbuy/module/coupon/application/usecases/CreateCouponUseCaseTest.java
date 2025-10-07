package com.miguelsperle.nexbuy.module.coupon.application.usecases;

import com.miguelsperle.nexbuy.module.coupon.application.ports.out.persistence.CouponRepository;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.inputs.CreateCouponUseCaseInput;
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

@ExtendWith(MockitoExtension.class)
public class CreateCouponUseCaseTest {
    @InjectMocks
    private CreateCouponUseCaseImpl createCouponUseCase;

    @Mock
    private CouponRepository couponRepository;

    @Test
    @DisplayName("Should create coupon")
    public void should_create_coupon() {
        final Coupon coupon = CouponBuilderTest.create(false);

        Mockito.when(this.couponRepository.existsByCode(Mockito.any())).thenReturn(false);

        Mockito.when(this.couponRepository.save(Mockito.any())).thenReturn(coupon);

        final CreateCouponUseCaseInput createCouponUseCaseInput = CreateCouponUseCaseInput.with(
                coupon.getCode(),
                coupon.getPercentage(),
                coupon.getMinimumPurchaseAmount(),
                coupon.getIsActive(),
                coupon.getExpiresIn()
        );

        this.createCouponUseCase.execute(createCouponUseCaseInput);

        Mockito.verify(this.couponRepository, Mockito.times(1)).existsByCode(Mockito.any());
        Mockito.verify(this.couponRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Should throw DomainException when coupon already exists")
    public void should_throw_DomainException_when_coupon_already_exists() {
        final Coupon coupon = CouponBuilderTest.create(false);

        Mockito.when(this.couponRepository.existsByCode(Mockito.any())).thenReturn(true);

        final CreateCouponUseCaseInput createCouponUseCaseInput = CreateCouponUseCaseInput.with(
                coupon.getCode(),
                coupon.getPercentage(),
                coupon.getMinimumPurchaseAmount(),
                coupon.getIsActive(),
                coupon.getExpiresIn()
        );

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.createCouponUseCase.execute(createCouponUseCaseInput)
        );

        final String expectedErrorMessage = "Coupon with this code already exists";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.couponRepository, Mockito.times(1)).existsByCode(Mockito.any());
    }
}
