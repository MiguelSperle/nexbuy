package com.miguelsperle.nexbuy.module.payment.infrastructure.adapters.in.web.controllers;

import com.miguelsperle.nexbuy.module.payment.application.ports.in.usecases.CreatePaymentUseCase;
import com.miguelsperle.nexbuy.module.payment.application.usecases.io.inputs.CreatePaymentUseCaseInput;
import com.miguelsperle.nexbuy.module.payment.application.usecases.io.outputs.CreatePaymentUseCaseOutput;
import com.miguelsperle.nexbuy.module.payment.infrastructure.adapters.in.web.dtos.requests.CreatePaymentRequest;
import com.miguelsperle.nexbuy.module.payment.infrastructure.adapters.in.web.dtos.responses.CreatePaymentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final CreatePaymentUseCase createPaymentUseCase;

    @PostMapping
    public ResponseEntity<CreatePaymentResponse> createPayment(@RequestBody @Valid CreatePaymentRequest createPaymentRequest) {
        final CreatePaymentUseCaseOutput createPaymentUseCaseOutput = this.createPaymentUseCase.execute(CreatePaymentUseCaseInput.with(
                createPaymentRequest.orderId(),
                createPaymentRequest.totalAmount()
        ));

        return ResponseEntity.ok().body(CreatePaymentResponse.from(createPaymentUseCaseOutput));
    }
}
