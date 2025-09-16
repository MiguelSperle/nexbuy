package com.miguelsperle.nexbuy.module.payment.infrastructure.adapters.in.web.controllers;

import com.miguelsperle.nexbuy.module.payment.application.ports.in.usecases.CreatePaymentUseCase;
import com.miguelsperle.nexbuy.module.payment.application.ports.in.usecases.GetPaymentUseCase;
import com.miguelsperle.nexbuy.module.payment.application.usecases.io.inputs.CreatePaymentUseCaseInput;
import com.miguelsperle.nexbuy.module.payment.application.usecases.io.inputs.GetPaymentUseCaseInput;
import com.miguelsperle.nexbuy.module.payment.application.usecases.io.outputs.CreatePaymentUseCaseOutput;
import com.miguelsperle.nexbuy.module.payment.application.usecases.io.outputs.GetPaymentUseCaseOutput;
import com.miguelsperle.nexbuy.module.payment.infrastructure.adapters.in.web.dtos.requests.CreatePaymentRequest;
import com.miguelsperle.nexbuy.module.payment.infrastructure.adapters.in.web.dtos.responses.CreatePaymentResponse;
import com.miguelsperle.nexbuy.module.payment.infrastructure.adapters.in.web.dtos.responses.GetPaymentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final CreatePaymentUseCase createPaymentUseCase;
    private final GetPaymentUseCase getPaymentUseCase;

    @PostMapping
    public ResponseEntity<CreatePaymentResponse> createPayment(@RequestBody @Valid CreatePaymentRequest createPaymentRequest) {
        final CreatePaymentUseCaseOutput createPaymentUseCaseOutput = this.createPaymentUseCase.execute(CreatePaymentUseCaseInput.with(
                createPaymentRequest.orderId(),
                createPaymentRequest.totalAmount()
        ));

        return ResponseEntity.ok().body(CreatePaymentResponse.from(createPaymentUseCaseOutput));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<GetPaymentResponse> getPayment(@PathVariable String orderId) {
        final GetPaymentUseCaseOutput getPaymentUseCaseOutput = this.getPaymentUseCase.execute(GetPaymentUseCaseInput.with(orderId));

        return ResponseEntity.ok().body(GetPaymentResponse.from(getPaymentUseCaseOutput));
    }
}
