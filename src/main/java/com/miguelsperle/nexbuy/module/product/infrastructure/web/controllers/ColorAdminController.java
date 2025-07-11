package com.miguelsperle.nexbuy.module.product.infrastructure.web.controllers;

import com.miguelsperle.nexbuy.core.infrastructure.dtos.MessageResponse;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.DeleteColorUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.RegisterColorUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.UpdateColorUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IDeleteColorUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IRegisterColorUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IUpdateColorUseCase;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests.RegisterColorRequest;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests.UpdateColorRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/colors")
@RequiredArgsConstructor
public class ColorAdminController {
    private final IRegisterColorUseCase registerColorUseCase;
    private final IUpdateColorUseCase updateColorUseCase;
    private final IDeleteColorUseCase deleteColorUseCase;

    @PostMapping
    public ResponseEntity<MessageResponse> registerColor(@RequestBody @Valid RegisterColorRequest registerColorRequest) {
        this.registerColorUseCase.execute(new RegisterColorUseCaseInput(registerColorRequest.name()));

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Color registered successfully"));
    }

    @PatchMapping("/{colorId}")
    public ResponseEntity<MessageResponse> updateColor(
            @PathVariable String colorId,
            @RequestBody @Valid UpdateColorRequest updateColorRequest
    ) {
        this.updateColorUseCase.execute(new UpdateColorUseCaseInput(
                colorId,
                updateColorRequest.name()
        ));

        return ResponseEntity.ok().body(new MessageResponse("Color updated successfully"));
    }

    @DeleteMapping("/{colorId}")
    public ResponseEntity<MessageResponse> deleteColor(@PathVariable String colorId) {
        this.deleteColorUseCase.execute(new DeleteColorUseCaseInput(colorId));

        return ResponseEntity.ok().body(new MessageResponse("Color deleted successfully"));
    }
}
