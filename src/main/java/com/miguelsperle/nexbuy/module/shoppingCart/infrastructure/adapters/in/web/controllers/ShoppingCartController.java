package com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.in.web.controllers;

import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.in.usecases.*;
import com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs.*;
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
    private final AddItemToShoppingCartUseCase addItemToShoppingCartUseCase;
    private final UpdateShoppingCartItemUseCase updateShoppingCartItemUseCase;
    private final GetShoppingCartUseCase getShoppingCartUseCase;
    private final DeleteShoppingCartItemUseCase deleteShoppingCartItemUseCase;
    private final DeleteAllShoppingCartItemsUseCase deleteAllShoppingCartItemsUseCase;

    @PostMapping("/{shoppingCartId}/items")
    public ResponseEntity<MessageResponse> addItemToShoppingCart(
            @PathVariable String shoppingCartId,
            @RequestBody @Valid AddToShoppingCartRequest addToShoppingCartRequest
    ) {
        this.addItemToShoppingCartUseCase.execute(AddItemToShoppingCartUseCaseInput.with(
                shoppingCartId,
                addToShoppingCartRequest.productId(),
                addToShoppingCartRequest.unitPrice()
        ));

        return ResponseEntity.ok().body(MessageResponse.from("Item successfully added to shopping cart"));
    }

    @PatchMapping("/{shoppingCartId}/items/{shoppingCartItemId}")
    public ResponseEntity<MessageResponse> updateShoppingCartItem(
            @PathVariable String shoppingCartId, @PathVariable String shoppingCartItemId,
            @RequestBody @Valid UpdateShoppingCartRequest updateShoppingCartRequest
    ) {
        this.updateShoppingCartItemUseCase.execute(UpdateShoppingCartItemUseCaseInput.with(
                shoppingCartId,
                shoppingCartItemId,
                updateShoppingCartRequest.quantity()
        ));

        return ResponseEntity.ok().body(MessageResponse.from("Shopping cart item updated successfully"));
    }

    @GetMapping("/me")
    public ResponseEntity<GetShoppingCartResponse> getShoppingCart() {
        final GetShoppingCartUseCaseOutput getShoppingCartUseCaseOutput = this.getShoppingCartUseCase.execute();

        return ResponseEntity.ok().body(GetShoppingCartResponse.from(getShoppingCartUseCaseOutput));
    }

    @DeleteMapping("/{shoppingCartId}/items/{shoppingCartItemId}")
    public ResponseEntity<MessageResponse> deleteShoppingCartItem(
            @PathVariable String shoppingCartId, @PathVariable String shoppingCartItemId
    ) {
        this.deleteShoppingCartItemUseCase.execute(DeleteShoppingCartItemUseCaseInput.with(
                shoppingCartId, shoppingCartItemId
        ));

        return ResponseEntity.ok().body(MessageResponse.from("Shopping cart item deleted successfully"));
    }

    @DeleteMapping("/{shoppingCartId}/items")
    public ResponseEntity<MessageResponse> deleteAllShoppingCartItems(@PathVariable String shoppingCartId) {
        this.deleteAllShoppingCartItemsUseCase.execute(DeleteAllShoppingCartItemsUseCaseInput.with(shoppingCartId));

        return ResponseEntity.ok().body(MessageResponse.from("All shopping cart items deleted successfully"));
    }
}
