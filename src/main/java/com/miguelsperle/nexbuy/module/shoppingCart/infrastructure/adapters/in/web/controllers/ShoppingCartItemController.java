package com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.in.web.controllers;

import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.in.usecases.UpdateShoppingCartItemUseCase;
import com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs.UpdateShoppingCartUseCaseInput;
import com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.in.web.dtos.requests.UpdateShoppingCartRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shopping-carts-items")
@RequiredArgsConstructor
public class ShoppingCartItemController {
    private final UpdateShoppingCartItemUseCase updateShoppingCartItemUseCase;

    @PatchMapping("/{itemId}")
    public ResponseEntity<Void> updateShoppingCartItem(
            @PathVariable String itemId,
            @RequestBody @Valid UpdateShoppingCartRequest updateShoppingCartRequest
    ) {
        this.updateShoppingCartItemUseCase.execute(UpdateShoppingCartUseCaseInput.with(
                itemId,
                updateShoppingCartRequest.quantity(),
                updateShoppingCartRequest.action()
        ));

        return ResponseEntity.noContent().build();
    }
}
