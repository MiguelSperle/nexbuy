package com.miguelsperle.nexbuy.module.freight.infrastructure.adapters.in.web.controllers;

import com.miguelsperle.nexbuy.module.freight.application.ports.in.usecases.ConsultFreightQuotesUseCase;
import com.miguelsperle.nexbuy.module.freight.application.usecases.io.inputs.ConsultFreightQuotesUseCaseInput;
import com.miguelsperle.nexbuy.module.freight.application.usecases.io.inputs.complement.ItemsComplementInput;
import com.miguelsperle.nexbuy.module.freight.application.usecases.io.outputs.ConsultFreightQuotesUseCaseOutput;
import com.miguelsperle.nexbuy.module.freight.infrastructure.adapters.in.web.dtos.requests.ConsultFreightQuotesRequest;
import com.miguelsperle.nexbuy.module.freight.infrastructure.adapters.in.web.dtos.responses.ConsultFreightQuotesResponse;
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
        final List<ItemsComplementInput> itemsComplementInput = consultFreightQuotesRequest.items().stream()
                .map(item -> ItemsComplementInput.with(
                        item.productId(),
                        item.height(),
                        item.width(),
                        item.length(),
                        item.weight(),
                        item.quantity()
                )).toList();

        final ConsultFreightQuotesUseCaseOutput consultFreightQuotesUseCaseOutput = this.consultFreightQuotesUseCase.execute(ConsultFreightQuotesUseCaseInput.with(
                consultFreightQuotesRequest.toCep(), itemsComplementInput
        ));

        return ResponseEntity.ok().body(ConsultFreightQuotesResponse.from(consultFreightQuotesUseCaseOutput));
    }
}
