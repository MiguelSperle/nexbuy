package com.miguelsperle.nexbuy.module.shoppingCart.application.usecases;

import com.miguelsperle.nexbuy.module.shoppingCart.application.abstractions.usecases.UpdateShoppingCartItemUseCase;
import com.miguelsperle.nexbuy.module.shoppingCart.application.abstractions.repositories.ShoppingCartItemRepository;
import com.miguelsperle.nexbuy.module.shoppingCart.application.abstractions.repositories.ShoppingCartRepository;
import com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs.UpdateShoppingCartItemUseCaseInput;
import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCart;
import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCartItem;
import com.miguelsperle.nexbuy.shared.application.abstractions.wrapper.TransactionManager;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

import java.math.BigDecimal;
import java.util.List;

public class UpdateShoppingCartItemUseCaseImpl implements UpdateShoppingCartItemUseCase {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartItemRepository shoppingCartItemRepository;
    private final TransactionManager transactionManager;

    public UpdateShoppingCartItemUseCaseImpl(
            ShoppingCartRepository shoppingCartRepository,
            ShoppingCartItemRepository shoppingCartItemRepository,
            TransactionManager transactionManager
    ) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartItemRepository = shoppingCartItemRepository;
        this.transactionManager = transactionManager;
    }

    @Override
    public void execute(UpdateShoppingCartItemUseCaseInput updateShoppingCartItemUseCaseInput) {
        final ShoppingCart shoppingCart = this.getShoppingCartById(updateShoppingCartItemUseCaseInput.shoppingCartId());

        final ShoppingCartItem shoppingCartItem = this.getShoppingCartItemById(updateShoppingCartItemUseCaseInput.shoppingCartItemId());

        this.transactionManager.runTransaction(() -> {
            final ShoppingCartItem updatedShoppingCartItem = shoppingCartItem.withQuantity(updateShoppingCartItemUseCaseInput.quantity());
            this.saveShoppingCartItem(updatedShoppingCartItem);

            final List<ShoppingCartItem> shoppingCartItems = this.getAllShoppingCartItemsByShoppingCartId(shoppingCart.getId());
            final BigDecimal calculatedTotalAmount = shoppingCartItems.stream().map(shoppingCartItemInList -> shoppingCartItemInList.getUnitPrice().multiply(BigDecimal.valueOf(shoppingCartItemInList.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            final ShoppingCart updatedShoppingCart = shoppingCart.withTotalAmount(calculatedTotalAmount);
            this.saveShoppingCart(updatedShoppingCart);
        });
    }

    private ShoppingCart getShoppingCartById(String id) {
        return this.shoppingCartRepository.findById(id).orElseThrow(() -> NotFoundException.with("Shopping cart not found"));
    }

    private ShoppingCartItem getShoppingCartItemById(String id) {
        return this.shoppingCartItemRepository.findById(id).orElseThrow(() -> NotFoundException.with("Shopping cart item not found"));
    }

    private List<ShoppingCartItem> getAllShoppingCartItemsByShoppingCartId(String shoppingCartId) {
        return this.shoppingCartItemRepository.findAllByShoppingCartId(shoppingCartId);
    }

    private void saveShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCartRepository.save(shoppingCart);
    }

    private void saveShoppingCartItem(ShoppingCartItem shoppingCartItem) {
        this.shoppingCartItemRepository.save(shoppingCartItem);
    }
}
