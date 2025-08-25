package com.miguelsperle.nexbuy.module.shoppingCart.application.usecases;

import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.in.usecases.GetShoppingCartUseCase;
import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.out.persistence.ShoppingCartItemRepository;
import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.out.persistence.ShoppingCartRepository;
import com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs.GetShoppingCartUseCaseInput;
import com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.outputs.GetShoppingCartUseCaseOutput;
import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCart;
import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCartItem;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

import java.util.List;

public class GetShoppingCartUseCaseImpl implements GetShoppingCartUseCase {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartItemRepository shoppingCartItemRepository;

    public GetShoppingCartUseCaseImpl(
            ShoppingCartRepository shoppingCartRepository,
            ShoppingCartItemRepository shoppingCartItemRepository
    ) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartItemRepository = shoppingCartItemRepository;
    }

    @Override
    public GetShoppingCartUseCaseOutput execute(GetShoppingCartUseCaseInput getShoppingCartUseCaseInput) {
        final ShoppingCart shoppingCart = this.getShoppingCartById(getShoppingCartUseCaseInput.shoppingCartId());

        final List<ShoppingCartItem> shoppingCartItems = this.getAllShoppingCartItemsByShoppingCartId(shoppingCart.getId());

        return GetShoppingCartUseCaseOutput.from(shoppingCart, shoppingCartItems);
    }

    private ShoppingCart getShoppingCartById(String id) {
        return this.shoppingCartRepository.findById(id).orElseThrow(() -> NotFoundException.with("Shopping cart not found"));
    }

    private List<ShoppingCartItem> getAllShoppingCartItemsByShoppingCartId(String shoppingCartId) {
        return this.shoppingCartItemRepository.findAllByShoppingCartId(shoppingCartId);
    }
}
