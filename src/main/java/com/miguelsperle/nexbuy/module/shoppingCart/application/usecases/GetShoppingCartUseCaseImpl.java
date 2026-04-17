package com.miguelsperle.nexbuy.module.shoppingCart.application.usecases;

import com.miguelsperle.nexbuy.module.shoppingCart.application.abstractions.usecases.GetShoppingCartUseCase;
import com.miguelsperle.nexbuy.module.shoppingCart.application.abstractions.repositories.ShoppingCartItemRepository;
import com.miguelsperle.nexbuy.module.shoppingCart.application.abstractions.repositories.ShoppingCartRepository;
import com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.outputs.GetShoppingCartUseCaseOutput;
import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCart;
import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCartItem;
import com.miguelsperle.nexbuy.shared.application.abstractions.services.SecurityService;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

import java.util.List;

public class GetShoppingCartUseCaseImpl implements GetShoppingCartUseCase {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartItemRepository shoppingCartItemRepository;
    private final SecurityService securityService;

    public GetShoppingCartUseCaseImpl(
            ShoppingCartRepository shoppingCartRepository,
            ShoppingCartItemRepository shoppingCartItemRepository,
            SecurityService securityService
    ) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartItemRepository = shoppingCartItemRepository;
        this.securityService = securityService;
    }

    @Override
    public GetShoppingCartUseCaseOutput execute() {
        final String authenticatedUserId = this.getAuthenticatedUserId();

        final ShoppingCart shoppingCart = this.getShoppingCartByUserId(authenticatedUserId);

        final List<ShoppingCartItem> shoppingCartItems = this.getAllShoppingCartItemsByShoppingCartId(shoppingCart.getId());

        return GetShoppingCartUseCaseOutput.from(shoppingCart, shoppingCartItems);
    }

    private String getAuthenticatedUserId() {
        return this.securityService.getUserId();
    }

    private ShoppingCart getShoppingCartByUserId(String userId) {
        return this.shoppingCartRepository.findByUserId(userId).orElseThrow(() -> NotFoundException.with("Shopping cart not found"));
    }

    private List<ShoppingCartItem> getAllShoppingCartItemsByShoppingCartId(String shoppingCartId) {
        return this.shoppingCartItemRepository.findAllByShoppingCartId(shoppingCartId);
    }
}
