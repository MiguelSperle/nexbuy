package com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.controllers;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetColorUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetColorUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.ports.in.IGetColorUseCase;
import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.dtos.responses.GetColorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/colors")
@RequiredArgsConstructor
public class ColorController {
    private final IGetColorUseCase getColorUseCase;

    @GetMapping("/{colorId}")
    public ResponseEntity<GetColorResponse> getColor(@PathVariable String colorId) {
        final GetColorUseCaseOutput getColorUseCaseOutput = this.getColorUseCase.execute(GetColorUseCaseInput.with(colorId));

        return ResponseEntity.ok().body(GetColorResponse.from(getColorUseCaseOutput));
    }
}
