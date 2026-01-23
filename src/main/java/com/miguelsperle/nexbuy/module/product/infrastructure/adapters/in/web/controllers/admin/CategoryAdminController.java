package com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.controllers.admin;

import com.miguelsperle.nexbuy.shared.infrastructure.adapters.in.web.dtos.responses.MessageResponse;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.DeleteCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.RegisterCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.UpdateCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.ports.in.usecases.DeleteCategoryUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.in.usecases.RegisterCategoryUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.in.usecases.UpdateCategoryUseCase;
import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.dtos.requests.RegisterCategoryRequest;
import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.dtos.requests.UpdateCategoryRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/categories")
@RequiredArgsConstructor
public class CategoryAdminController {
    private final RegisterCategoryUseCase registerCategoryUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;

    @PostMapping
    public ResponseEntity<MessageResponse> registerCategory(@RequestBody @Valid RegisterCategoryRequest registerCategoryRequest) {
        this.registerCategoryUseCase.execute(RegisterCategoryUseCaseInput.with(
                registerCategoryRequest.name()
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(MessageResponse.from("Category registered successfully"));
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<MessageResponse> updateCategory(
            @PathVariable String categoryId,
            @RequestBody @Valid UpdateCategoryRequest updateCategoryRequest
    ) {
        this.updateCategoryUseCase.execute(UpdateCategoryUseCaseInput.with(
                categoryId,
                updateCategoryRequest.name()
        ));

        return ResponseEntity.ok().body(MessageResponse.from("Category updated successfully"));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<MessageResponse> deleteCategory(@PathVariable String categoryId) {
        this.deleteCategoryUseCase.execute(DeleteCategoryUseCaseInput.with(categoryId));

        return ResponseEntity.ok().body(MessageResponse.from("Category deleted successfully"));
    }
}
