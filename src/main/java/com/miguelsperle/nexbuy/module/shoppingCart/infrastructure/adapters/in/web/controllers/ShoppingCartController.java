package com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.in.web.controllers;

import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.in.usecases.AddToShoppingCartUseCase;
import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.in.usecases.DeleteShoppingCartItemUseCase;
import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.in.usecases.GetShoppingCartUseCase;
import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.in.usecases.UpdateShoppingCartItemUseCase;
import com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs.AddToShoppingCartUseCaseInput;
import com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs.DeleteShoppingCartItemUseCaseInput;
import com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs.GetShoppingCartUseCaseInput;
import com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs.UpdateShoppingCartUseCaseInput;
import com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.outputs.GetShoppingCartUseCaseOutput;
import com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.in.web.dtos.requests.AddToShoppingCartRequest;
import com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.in.web.dtos.requests.UpdateShoppingCartRequest;
import com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.in.web.dtos.responses.GetShoppingCartResponse;
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
    private final UpdateShoppingCartItemUseCase updateShoppingCartItemUseCase;
    private final GetShoppingCartUseCase getShoppingCartUseCase;
    private final DeleteShoppingCartItemUseCase deleteShoppingCartItemUseCase;

    @PostMapping("/{cartId}/items")
    public ResponseEntity<MessageResponse> addToShoppingCart(
            @PathVariable String cartId,
            @RequestBody @Valid AddToShoppingCartRequest addToShoppingCartRequest
    ) {
        this.addToShoppingCartUseCase.execute(AddToShoppingCartUseCaseInput.with(
                cartId,
                addToShoppingCartRequest.productId(),
                addToShoppingCartRequest.unitPrice()
        ));

        return ResponseEntity.ok().body(MessageResponse.from("Added to shopping cart successfully"));
    }

    @PatchMapping("/{cartId}/items/{itemId}")
    public ResponseEntity<Void> updateShoppingCartItem(
            @PathVariable String cartId, @PathVariable String itemId,
            @RequestBody @Valid UpdateShoppingCartRequest updateShoppingCartRequest
    ) {
        this.updateShoppingCartItemUseCase.execute(UpdateShoppingCartUseCaseInput.with(
                cartId,
                itemId,
                updateShoppingCartRequest.quantity(),
                updateShoppingCartRequest.action()
        ));

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<GetShoppingCartResponse> getShoppingCart(@PathVariable String cartId) {
        final GetShoppingCartUseCaseOutput getShoppingCartUseCaseOutput =
                this.getShoppingCartUseCase.execute(GetShoppingCartUseCaseInput.with(cartId));

        return ResponseEntity.ok().body(GetShoppingCartResponse.from(getShoppingCartUseCaseOutput));
    }

    @DeleteMapping("/{cartId}/items/{itemId}")
    public ResponseEntity<Void> deleteShoppingCartItem(
            @PathVariable String cartId, @PathVariable String itemId
    ) {
        this.deleteShoppingCartItemUseCase.execute(DeleteShoppingCartItemUseCaseInput.with(
                cartId, itemId
        ));

        return ResponseEntity.noContent().build();
    }
}
