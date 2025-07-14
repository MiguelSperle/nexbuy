package com.miguelsperle.nexbuy.module.product.infrastructure.web.controllers;

import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetColorsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IGetColorsUseCase;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses.GetColorsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/colors")
@RequiredArgsConstructor
public class ColorController {
    private final IGetColorsUseCase getColorsUseCase;

    @GetMapping
    public ResponseEntity<List<GetColorsResponse>> getColors() {
        final GetColorsUseCaseOutput getColorsUseCaseOutput = this.getColorsUseCase.execute();

        return ResponseEntity.ok().body(GetColorsResponse.fromOutput(getColorsUseCaseOutput));
    }
}
