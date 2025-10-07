package com.miguelsperle.nexbuy.module.coupon.application.usecases;

import com.miguelsperle.nexbuy.module.coupon.application.ports.out.persistence.CouponRepository;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.inputs.DeleteCouponUseCaseInput;
import com.miguelsperle.nexbuy.module.coupon.domain.entities.Coupon;
import com.miguelsperle.nexbuy.module.coupon.utils.CouponBuilderTest;
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
public class DeleteCouponUseCaseTest {
    @InjectMocks
    private DeleteCouponUseCaseImpl deleteCouponUseCase;

    @Mock
    private CouponRepository couponRepository;

    @Test
    @DisplayName("Should delete coupon")
    public void should_delete_coupon() {
        final Coupon coupon = CouponBuilderTest.create(false);

        Mockito.when(this.couponRepository.findById(Mockito.any())).thenReturn(Optional.of(coupon));

        Mockito.doNothing().when(this.couponRepository).deleteById(Mockito.any());

        this.deleteCouponUseCase.execute(DeleteCouponUseCaseInput.with(coupon.getId()));

        Mockito.verify(this.couponRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.couponRepository, Mockito.times(1)).deleteById(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when coupon does not exist")
    public void should_throw_NotFoundException_when_coupon_does_not_exist() {
        final Coupon coupon = CouponBuilderTest.create(false);

        Mockito.when(this.couponRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.deleteCouponUseCase.execute(DeleteCouponUseCaseInput.with(coupon.getId()))
        );

        final String expectedErrorMessage = "Coupon not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.couponRepository, Mockito.times(1)).findById(Mockito.any());
    }
}
