package com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.in.web.controllers.admin;

import com.miguelsperle.nexbuy.core.infrastructure.adapters.in.web.dtos.MessageResponse;
import com.miguelsperle.nexbuy.module.inventory.application.ports.in.IIncreaseInventoryUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.IncreaseInventoryUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.in.web.dtos.requests.IncreaseInventoryRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/inventories")
@RequiredArgsConstructor
public class InventoryAdminController {
    private final IIncreaseInventoryUseCase increaseInventoryUseCase;

    @PatchMapping("/{sku}/increase")
    public ResponseEntity<MessageResponse> increaseInventory(
            @PathVariable String sku,
            @RequestBody @Valid IncreaseInventoryRequest increaseInventoryRequest
    ) {
        this.increaseInventoryUseCase.execute(IncreaseInventoryUseCaseInput.with(
                sku,
                increaseInventoryRequest.quantity()
        ));

        return ResponseEntity.ok().body(MessageResponse.from("The quantity was successfully increased"));
    }
}
