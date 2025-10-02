package com.miguelsperle.nexbuy.module.payment.application.usecases;

import com.miguelsperle.nexbuy.module.payment.application.ports.out.persistence.PaymentRepository;
import com.miguelsperle.nexbuy.module.payment.application.ports.out.services.PaymentService;
import com.miguelsperle.nexbuy.module.payment.application.usecases.io.inputs.CreatePaymentUseCaseInput;
import com.miguelsperle.nexbuy.module.payment.application.usecases.io.outputs.CreatePaymentUseCaseOutput;
import com.miguelsperle.nexbuy.module.payment.domain.entities.Payment;
import com.miguelsperle.nexbuy.module.payment.domain.enums.PaymentStatus;
import com.miguelsperle.nexbuy.module.payment.utils.PaymentBuilderTest;
import com.miguelsperle.nexbuy.shared.application.ports.out.services.SecurityContextService;
import com.miguelsperle.nexbuy.shared.application.ports.out.transaction.TransactionExecutor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreatePaymentUseCaseTest {
    @InjectMocks
    private CreatePaymentUseCaseImpl createPaymentUseCase;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentService paymentService;

    @Mock
    private TransactionExecutor transactionExecutor;

    @Mock
    private SecurityContextService securityContextService;

    @Test
    @DisplayName("Should create payment")
    public void should_create_payment() {
        final Payment payment = PaymentBuilderTest.create(PaymentStatus.PENDING);
        final String sessionUrl = "test-session-url";

        Mockito.when(this.securityContextService.getAuthenticatedUserId()).thenReturn(payment.getUserId());

        Mockito.doAnswer(invocationOnMock -> {
            final Runnable runnable = invocationOnMock.getArgument(0);
            runnable.run();
            return runnable;
        }).when(this.transactionExecutor).runTransaction(Mockito.any());

        Mockito.when(this.paymentRepository.save(Mockito.any())).thenReturn(payment);

        Mockito.when(this.paymentService.createCheckoutSession(Mockito.any(), Mockito.anyLong())).thenReturn(sessionUrl);

        final CreatePaymentUseCaseInput createPaymentUseCaseInput = CreatePaymentUseCaseInput.with(
                payment.getOrderId(),
                payment.getTotalAmount()
        );

        final CreatePaymentUseCaseOutput createPaymentUseCaseOutput = this.createPaymentUseCase.execute(createPaymentUseCaseInput);

        Assertions.assertNotNull(createPaymentUseCaseOutput);
        Assertions.assertNotNull(createPaymentUseCaseOutput.sessionUrl());

        Assertions.assertEquals(sessionUrl, createPaymentUseCaseOutput.sessionUrl());

        Mockito.verify(this.securityContextService, Mockito.times(1)).getAuthenticatedUserId();
        Mockito.verify(this.transactionExecutor, Mockito.times(1)).runTransaction(Mockito.any());
        Mockito.verify(this.paymentRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.paymentService, Mockito.times(1)).createCheckoutSession(Mockito.any(), Mockito.anyLong());
    }
}
