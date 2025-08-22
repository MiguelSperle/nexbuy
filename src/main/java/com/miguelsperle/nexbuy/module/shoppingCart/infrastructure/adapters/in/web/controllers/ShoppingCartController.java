package com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.in.web.controllers;

import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.in.usecases.AddItemToShoppingCartUseCase;
import com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs.AddItemToShoppingCartUseCaseInput;
import com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.in.web.dtos.requests.AddItemToShoppingCartRequest;
import com.miguelsperle.nexbuy.shared.infrastructure.adapters.in.web.dtos.responses.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shopping-carts")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final AddItemToShoppingCartUseCase addItemToShoppingCartUseCase;

    @PostMapping("/items")
    public ResponseEntity<MessageResponse> addItemToShoppingCart(@RequestBody @Valid AddItemToShoppingCartRequest addItemToShoppingCartRequest) {
        this.addItemToShoppingCartUseCase.execute(AddItemToShoppingCartUseCaseInput.with(
                addItemToShoppingCartRequest.productId(),
                addItemToShoppingCartRequest.unitPrice(),
                addItemToShoppingCartRequest.quantity()
        ));

        return ResponseEntity.ok().body(MessageResponse.from("Item successfully added to shopping cart"));
    }
}
