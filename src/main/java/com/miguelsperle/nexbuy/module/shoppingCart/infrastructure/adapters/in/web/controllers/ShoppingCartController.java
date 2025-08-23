package com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.in.web.controllers;

import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.in.usecases.AddToShoppingCartUseCase;
import com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs.AddToShoppingCartUseCaseInput;
import com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.in.web.dtos.requests.AddToShoppingCartRequest;
import com.miguelsperle.nexbuy.shared.infrastructure.adapters.in.web.dtos.responses.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shopping-carts")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final AddToShoppingCartUseCase addToShoppingCartUseCase;

    @PostMapping
    public ResponseEntity<MessageResponse> addToShoppingCart(@RequestBody @Valid AddToShoppingCartRequest addToShoppingCartRequest) {
        this.addToShoppingCartUseCase.execute(AddToShoppingCartUseCaseInput.with(
                addToShoppingCartRequest.productId(),
                addToShoppingCartRequest.unitPrice()
        ));

        return ResponseEntity.ok().body(MessageResponse.from("Added to shopping cart successfully"));
    }
}
