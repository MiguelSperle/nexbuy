package com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.in.web.controllers.admin;

import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.shared.domain.pagination.SearchQuery;
import com.miguelsperle.nexbuy.shared.infrastructure.adapters.in.web.dtos.responses.MessageResponse;
import com.miguelsperle.nexbuy.module.inventory.application.ports.in.DecreaseInventoryUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.ports.in.GetInventoriesUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.ports.in.GetInventoryUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.ports.in.IncreaseInventoryUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.DecreaseInventoryUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.GetInventoriesUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.GetInventoryUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.IncreaseInventoryUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs.GetInventoriesUseCaseOutput;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs.GetInventoryUseCaseOutput;
import com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.in.web.dtos.requests.DecreaseInventoryRequest;
import com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.in.web.dtos.requests.IncreaseInventoryRequest;
import com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.in.web.dtos.responses.GetInventoriesResponse;
import com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.in.web.dtos.responses.GetInventoryResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/inventories")
@RequiredArgsConstructor
public class InventoryAdminController {
    private final GetInventoriesUseCase getInventoriesUseCase;
    private final GetInventoryUseCase getInventoryUseCase;
    private final IncreaseInventoryUseCase increaseInventoryUseCase;
    private final DecreaseInventoryUseCase decreaseInventoryUseCase;

    @GetMapping
    public ResponseEntity<Pagination<GetInventoriesResponse>> getInventories(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "quantity") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
            @RequestParam Map<String, String> filters
    ) {
        final GetInventoriesUseCaseOutput getInventoriesUseCaseOutput = this.getInventoriesUseCase.execute(GetInventoriesUseCaseInput.with(
                SearchQuery.newSearchQuery(page, perPage, sort, direction, filters)
        ));

        return ResponseEntity.ok().body(GetInventoriesResponse.from(getInventoriesUseCaseOutput));
    }

    @GetMapping("/{sku}")
    public ResponseEntity<GetInventoryResponse> getInventory(@PathVariable String sku) {
        final GetInventoryUseCaseOutput getInventoryUseCaseOutput = this.getInventoryUseCase.execute(GetInventoryUseCaseInput.with(
                sku
        ));

        return ResponseEntity.ok().body(GetInventoryResponse.from(getInventoryUseCaseOutput));
    }

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

    @PatchMapping("/{sku}/decrease")
    public ResponseEntity<MessageResponse> decreaseInventory(
            @PathVariable String sku,
            @RequestBody @Valid DecreaseInventoryRequest decreaseInventoryRequest
    ) {
        this.decreaseInventoryUseCase.execute(DecreaseInventoryUseCaseInput.with(
                sku,
                decreaseInventoryRequest.quantity()
        ));

        return ResponseEntity.ok().body(MessageResponse.from("The quantity was successfully decreased"));
    }
}
