package com.miguelsperle.nexbuy.module.shipping.infrastructure.adapters.in.web.controllers;

import com.miguelsperle.nexbuy.module.shipping.application.ports.in.usecases.ConsultFreightQuotesUseCase;
import com.miguelsperle.nexbuy.module.shipping.application.usecases.io.inputs.ConsultFreightQuotesUseCaseInput;
import com.miguelsperle.nexbuy.module.shipping.application.usecases.io.inputs.complement.ProductsComplementInput;
import com.miguelsperle.nexbuy.module.shipping.application.usecases.io.outputs.ConsultFreightQuotesUseCaseOutput;
import com.miguelsperle.nexbuy.module.shipping.infrastructure.adapters.in.web.dtos.requests.ConsultFreightQuotesRequest;
import com.miguelsperle.nexbuy.module.shipping.infrastructure.adapters.in.web.dtos.responses.ConsultFreightQuotesResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/freights")
@RequiredArgsConstructor
public class FreightController {
    private final ConsultFreightQuotesUseCase consultFreightQuotesUseCase;

    @PostMapping("/quotes")
    public ResponseEntity<List<ConsultFreightQuotesResponse>> consultFreightQuotes(@RequestBody @Valid ConsultFreightQuotesRequest consultFreightQuotesRequest) {
        final List<ProductsComplementInput> productsComplementInput = consultFreightQuotesRequest.products().stream()
                .map(product -> ProductsComplementInput.with(
                        product.id(),
                        product.height(),
                        product.width(),
                        product.length(),
                        product.weight(),
                        product.quantity()
                )).toList();

        final ConsultFreightQuotesUseCaseOutput consultFreightQuotesUseCaseOutput = this.consultFreightQuotesUseCase.execute(ConsultFreightQuotesUseCaseInput.with(
                consultFreightQuotesRequest.toCep(), productsComplementInput
        ));

        return ResponseEntity.ok().body(ConsultFreightQuotesResponse.from(consultFreightQuotesUseCaseOutput));
    }
}
