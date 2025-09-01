package com.miguelsperle.nexbuy.module.payments.infrastructure.adapters.in.web.controllers;

import com.miguelsperle.nexbuy.module.payments.application.ports.in.usecases.CreatePaymentMethodUseCase;
import com.miguelsperle.nexbuy.module.payments.application.usecases.io.inputs.CreatePaymentMethodUseCaseInput;
import com.miguelsperle.nexbuy.module.payments.infrastructure.adapters.in.web.dtos.CreatePaymentMethodRequest;
import com.miguelsperle.nexbuy.shared.infrastructure.adapters.in.web.dtos.responses.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment-methods")
@RequiredArgsConstructor
public class PaymentMethodController {
    private final CreatePaymentMethodUseCase createPaymentMethodUseCase;

    @PostMapping
    public ResponseEntity<MessageResponse> createPaymentMethod(@RequestBody @Valid CreatePaymentMethodRequest createPaymentMethodRequest) {
        this.createPaymentMethodUseCase.execute(CreatePaymentMethodUseCaseInput.with(
                createPaymentMethodRequest.name()
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(MessageResponse.from("Payment method created successfully"));
    }
}
