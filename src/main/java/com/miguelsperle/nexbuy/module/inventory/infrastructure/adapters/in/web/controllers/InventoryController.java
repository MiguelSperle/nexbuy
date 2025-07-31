package com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.in.web.controllers;

import com.miguelsperle.nexbuy.module.inventory.application.ports.in.CheckInventoryAvailabilityUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.CheckInventoryAvailabilityUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs.CheckInventoryAvailabilityUseCaseOutput;
import com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.in.web.dtos.responses.CheckInventoryAvailabilityResponse;
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
    private final CheckInventoryAvailabilityUseCase checkInventoryAvailabilityUseCase;

    @GetMapping("/{sku}/availability")
    public ResponseEntity<CheckInventoryAvailabilityResponse> checkInventoryAvailability(@PathVariable String sku) {
        final CheckInventoryAvailabilityUseCaseOutput checkInventoryAvailabilityUseCaseOutput =
                this.checkInventoryAvailabilityUseCase.execute(CheckInventoryAvailabilityUseCaseInput.with(sku));

        return ResponseEntity.ok().body(CheckInventoryAvailabilityResponse.from(checkInventoryAvailabilityUseCaseOutput));
    }
}
