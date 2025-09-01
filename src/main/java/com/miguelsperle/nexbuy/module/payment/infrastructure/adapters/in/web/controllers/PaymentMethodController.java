package com.miguelsperle.nexbuy.module.payment.infrastructure.adapters.in.web.controllers;

import com.miguelsperle.nexbuy.module.payment.application.ports.in.usecases.GetPaymentMethodsUseCase;
import com.miguelsperle.nexbuy.module.payment.application.usecases.io.outputs.GetPaymentMethodsUseCaseOutput;
import com.miguelsperle.nexbuy.module.payment.infrastructure.adapters.in.web.dtos.responses.GetPaymentMethodsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment-methods")
@RequiredArgsConstructor
public class PaymentMethodController {
    private final GetPaymentMethodsUseCase getPaymentMethodsUseCase;

    @GetMapping
    public ResponseEntity<List<GetPaymentMethodsResponse>> getPaymentMethods() {
        final GetPaymentMethodsUseCaseOutput getPaymentMethodsUseCaseOutput = this.getPaymentMethodsUseCase.execute();

        return ResponseEntity.ok().body(GetPaymentMethodsResponse.from(getPaymentMethodsUseCaseOutput));
    }
}
