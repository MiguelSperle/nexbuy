package com.miguelsperle.nexbuy.module.payment.application.usecases;

import com.miguelsperle.nexbuy.module.payment.application.ports.out.persistence.PaymentRepository;
import com.miguelsperle.nexbuy.module.payment.application.usecases.io.inputs.GetPaymentUseCaseInput;
import com.miguelsperle.nexbuy.module.payment.application.usecases.io.outputs.GetPaymentUseCaseOutput;
import com.miguelsperle.nexbuy.module.payment.domain.entities.Payment;
import com.miguelsperle.nexbuy.module.payment.domain.enums.PaymentStatus;
import com.miguelsperle.nexbuy.module.payment.utils.PaymentBuilderTest;
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
public class GetPaymentUseCaseTest {
    @InjectMocks
    private GetPaymentUseCaseImpl getPaymentUseCase;

    @Mock
    private PaymentRepository paymentRepository;

    @Test
    @DisplayName("Should get payment")
    public void should_get_payment() {
        final Payment payment = PaymentBuilderTest.create(PaymentStatus.PENDING);

        Mockito.when(this.paymentRepository.findByOrderId(Mockito.any())).thenReturn(Optional.of(payment));

        final GetPaymentUseCaseInput getPaymentUseCaseInput = GetPaymentUseCaseInput.with(payment.getOrderId());

        final GetPaymentUseCaseOutput getPaymentUseCaseOutput = this.getPaymentUseCase.execute(getPaymentUseCaseInput);

        Assertions.assertNotNull(getPaymentUseCaseOutput);
        Assertions.assertNotNull(getPaymentUseCaseOutput.payment());

        Assertions.assertEquals(payment, getPaymentUseCaseOutput.payment());

        Mockito.verify(this.paymentRepository, Mockito.times(1)).findByOrderId(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when payment does not exist")
    public void should_throw_NotFoundException_when_payment_does_not_exist() {
        final Payment payment = PaymentBuilderTest.create(PaymentStatus.PENDING);

        Mockito.when(this.paymentRepository.findByOrderId(Mockito.any())).thenReturn(Optional.empty());

        final GetPaymentUseCaseInput getPaymentUseCaseInput = GetPaymentUseCaseInput.with(payment.getOrderId());

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.getPaymentUseCase.execute(getPaymentUseCaseInput)
        );

        final String expectedErrorMessage = "Payment not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
