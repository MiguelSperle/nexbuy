package com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.controllers.admin;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetColorsUseCaseInput;
import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.shared.domain.pagination.SearchQuery;
import com.miguelsperle.nexbuy.shared.infrastructure.adapters.in.web.dtos.responses.MessageResponse;
import com.miguelsperle.nexbuy.module.product.application.ports.in.usecases.DeleteColorUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.in.usecases.GetColorsUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.in.usecases.RegisterColorUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.in.usecases.UpdateColorUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.DeleteColorUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.RegisterColorUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.UpdateColorUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetColorsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.dtos.requests.RegisterColorRequest;
import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.dtos.requests.UpdateColorRequest;
import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.dtos.responses.GetColorsResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/colors")
@RequiredArgsConstructor
public class ColorAdminController {
    private final RegisterColorUseCase registerColorUseCase;
    private final UpdateColorUseCase updateColorUseCase;
    private final DeleteColorUseCase deleteColorUseCase;
    private final GetColorsUseCase getColorsUseCase;

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
    public ResponseEntity<Pagination<GetColorsResponse>> getColors(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") int perPage,
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "sort", required = false, defaultValue = "name") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction
    ) {
        final GetColorsUseCaseOutput getColorsUseCaseOutput = this.getColorsUseCase.execute(GetColorsUseCaseInput.with(
                SearchQuery.newSearchQuery(page, perPage, search, sort, direction)
        ));

        return ResponseEntity.ok().body(GetColorsResponse.from(getColorsUseCaseOutput));
    }
}
