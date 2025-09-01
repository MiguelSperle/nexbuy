package com.miguelsperle.nexbuy.module.payment.infrastructure.adapters.in.web.controllers.admin;

import com.miguelsperle.nexbuy.module.payment.application.ports.in.usecases.CreatePaymentMethodUseCase;
import com.miguelsperle.nexbuy.module.payment.application.ports.in.usecases.UpdatePaymentMethodUseCase;
import com.miguelsperle.nexbuy.module.payment.application.usecases.io.inputs.CreatePaymentMethodUseCaseInput;
import com.miguelsperle.nexbuy.module.payment.application.usecases.io.inputs.UpdatePaymentMethodUseCaseInput;
import com.miguelsperle.nexbuy.module.payment.infrastructure.adapters.in.web.dtos.requests.CreatePaymentMethodRequest;
import com.miguelsperle.nexbuy.module.payment.infrastructure.adapters.in.web.dtos.requests.UpdatePaymentMethodRequest;
import com.miguelsperle.nexbuy.shared.infrastructure.adapters.in.web.dtos.responses.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/payment-methods")
@RequiredArgsConstructor
public class PaymentMethodAdminController {
    private final CreatePaymentMethodUseCase createPaymentMethodUseCase;
    private final UpdatePaymentMethodUseCase updatePaymentMethodUseCase;

    @PostMapping
    public ResponseEntity<MessageResponse> createPaymentMethod(@RequestBody @Valid CreatePaymentMethodRequest createPaymentMethodRequest) {
        this.createPaymentMethodUseCase.execute(CreatePaymentMethodUseCaseInput.with(
                createPaymentMethodRequest.name()
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(MessageResponse.from("Payment method created successfully"));
    }

    @PatchMapping("/{paymentMethodId}")
    public ResponseEntity<MessageResponse> updatePaymentMethod(
            @PathVariable String paymentMethodId,
            @RequestBody @Valid UpdatePaymentMethodRequest updatePaymentMethodRequest
    ) {
        this.updatePaymentMethodUseCase.execute(UpdatePaymentMethodUseCaseInput.with(
                paymentMethodId, updatePaymentMethodRequest.name()
        ));

        return ResponseEntity.ok().body(MessageResponse.from("Payment method updated successfully"));
    }
}
