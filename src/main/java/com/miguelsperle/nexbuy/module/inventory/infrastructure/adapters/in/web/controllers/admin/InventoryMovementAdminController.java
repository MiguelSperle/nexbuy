package com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.in.web.controllers.admin;

import com.miguelsperle.nexbuy.core.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.core.domain.pagination.SearchQuery;
import com.miguelsperle.nexbuy.module.inventory.application.ports.in.GetInventoryMovementsUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.GetInventoryMovementsUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs.GetInventoryMovementsUseCaseOutput;
import com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.in.web.dtos.responses.GetInventoryMovementsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/inventory-movements")
@RequiredArgsConstructor
public class InventoryMovementAdminController {
    private final GetInventoryMovementsUseCase getInventoryMovementsUseCase;

    @GetMapping("/{sku}")
    public ResponseEntity<Pagination<GetInventoryMovementsResponse>> getInventoryMovements(
            @PathVariable String sku,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "createdAt") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "desc") String direction,
            @RequestParam Map<String, String> filters
    ) {
        final GetInventoryMovementsUseCaseOutput getInventoryMovementsUseCaseOutput = this.getInventoryMovementsUseCase.execute(
                GetInventoryMovementsUseCaseInput.with(sku, SearchQuery.newSearchQuery(page, perPage, sort, direction, filters))
        );

        return ResponseEntity.ok().body(GetInventoryMovementsResponse.from(getInventoryMovementsUseCaseOutput));
    }
}
