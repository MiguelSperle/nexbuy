package com.miguelsperle.nexbuy.module.product.infrastructure.web.controllers;

import com.miguelsperle.nexbuy.core.infrastructure.dtos.MessageResponse;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.DeleteCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.GetCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.RegisterCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.UpdateCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetCategoryUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IDeleteCategoryUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IGetCategoryUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IRegisterCategoryUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IUpdateCategoryUseCase;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests.RegisterCategoryRequest;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests.UpdateCategoryRequest;
import com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses.GetCategoryResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/categories")
@RequiredArgsConstructor
public class CategoryAdminController {
    private final IRegisterCategoryUseCase registerCategoryUseCase;
    private final IUpdateCategoryUseCase updateCategoryUseCase;
    private final IDeleteCategoryUseCase deleteCategoryUseCase;
    private final IGetCategoryUseCase getCategoryUseCase;

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

    @GetMapping("/{categoryId}")
    public ResponseEntity<GetCategoryResponse> getCategory(@PathVariable String categoryId) {
        final GetCategoryUseCaseOutput getCategoryUseCaseOutput = this.getCategoryUseCase.execute(GetCategoryUseCaseInput.with(categoryId));

        return ResponseEntity.ok().body(GetCategoryResponse.from(getCategoryUseCaseOutput));
    }
}
