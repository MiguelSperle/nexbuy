package com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.in.web.controllers;

import com.miguelsperle.nexbuy.module.inventory.application.ports.in.GetInventoryUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.GetInventoryUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs.GetInventoryUseCaseOutput;
import com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.in.web.dtos.responses.GetInventoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/inventories")
@RequiredArgsConstructor
public class InventoryController {
    private final GetInventoryUseCase getInventoryUseCase;

    @GetMapping("/{sku}")
    public ResponseEntity<GetInventoryResponse> getInventory(@PathVariable String sku) {
        final GetInventoryUseCaseOutput getInventoryUseCaseOutput = this.getInventoryUseCase.execute(GetInventoryUseCaseInput.with(
                sku
        ));

        return ResponseEntity.ok().body(GetInventoryResponse.from(getInventoryUseCaseOutput));
    }
}
