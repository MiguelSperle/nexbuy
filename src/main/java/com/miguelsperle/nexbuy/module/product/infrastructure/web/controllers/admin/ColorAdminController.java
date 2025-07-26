package com.miguelsperle.nexbuy.module.product.infrastructure.web.controllers.admin;

import com.miguelsperle.nexbuy.core.infrastructure.adapters.in.rest.dtos.MessageResponse;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.DeleteColorUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.RegisterColorUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.UpdateColorUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetColorsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.*;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests.RegisterColorRequest;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests.UpdateColorRequest;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses.GetColorsResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/colors")
@RequiredArgsConstructor
public class ColorAdminController {
    private final IRegisterColorUseCase registerColorUseCase;
    private final IUpdateColorUseCase updateColorUseCase;
    private final IDeleteColorUseCase deleteColorUseCase;
    private final IGetColorsUseCase getColorsUseCase;

    @PostMapping
    public ResponseEntity<MessageResponse> registerColor(@RequestBody @Valid RegisterColorRequest registerColorRequest) {
        this.registerColorUseCase.execute(RegisterColorUseCaseInput.with(
                registerColorRequest.name()
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(MessageResponse.from("Color registered successfully"));
    }

    @PatchMapping("/{colorId}")
    public ResponseEntity<MessageResponse> updateColor(
            @PathVariable String colorId,
            @RequestBody @Valid UpdateColorRequest updateColorRequest
    ) {
        this.updateColorUseCase.execute(UpdateColorUseCaseInput.with(
                colorId,
                updateColorRequest.name()
        ));

        return ResponseEntity.ok().body(MessageResponse.from("Color updated successfully"));
    }

    @DeleteMapping("/{colorId}")
    public ResponseEntity<MessageResponse> deleteColor(@PathVariable String colorId) {
        this.deleteColorUseCase.execute(DeleteColorUseCaseInput.with(colorId));

        return ResponseEntity.ok().body(MessageResponse.from("Color deleted successfully"));
    }

    @GetMapping
    public ResponseEntity<List<GetColorsResponse>> getColors() {
        final GetColorsUseCaseOutput getColorsUseCaseOutput = this.getColorsUseCase.execute();

        return ResponseEntity.ok().body(GetColorsResponse.from(getColorsUseCaseOutput));
    }
}
