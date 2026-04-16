package com.miguelsperle.nexbuy.module.shoppingCart.application.usecases;

import com.miguelsperle.nexbuy.module.shoppingCart.application.abstractions.usecases.DeleteShoppingCartItemUseCase;
import com.miguelsperle.nexbuy.module.shoppingCart.application.abstractions.repositories.ShoppingCartItemRepository;
import com.miguelsperle.nexbuy.module.shoppingCart.application.abstractions.repositories.ShoppingCartRepository;
import com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs.DeleteShoppingCartItemUseCaseInput;
import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCart;
import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCartItem;
import com.miguelsperle.nexbuy.shared.application.abstractions.wrapper.TransactionManager;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

import java.math.BigDecimal;
import java.util.List;

public class DeleteShoppingCartItemUseCaseImpl implements DeleteShoppingCartItemUseCase {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartItemRepository shoppingCartItemRepository;
    private final TransactionManager transactionManager;

    public DeleteShoppingCartItemUseCaseImpl(
            ShoppingCartRepository shoppingCartRepository,
            ShoppingCartItemRepository shoppingCartItemRepository,
            TransactionManager transactionManager
    ) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartItemRepository = shoppingCartItemRepository;
        this.transactionManager = transactionManager;
    }

    @Override
    public void execute(DeleteShoppingCartItemUseCaseInput deleteShoppingCartItemUseCaseInput) {
        final ShoppingCart shoppingCart = this.getShoppingCartById(deleteShoppingCartItemUseCaseInput.shoppingCartId());

        final ShoppingCartItem shoppingCartItem = this.getShoppingCartItemById(deleteShoppingCartItemUseCaseInput.shoppingCartItemId());

        this.transactionManager.runTransaction(() -> {
            this.deleteShoppingCartItemById(shoppingCartItem.getId());

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

    private void deleteShoppingCartItemById(String id) {
        this.shoppingCartItemRepository.deleteById(id);
    }

    private List<ShoppingCartItem> getAllShoppingCartItemsByShoppingCartId(String shoppingCartId) {
        return this.shoppingCartItemRepository.findAllByShoppingCartId(shoppingCartId);
    }

    private void saveShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCartRepository.save(shoppingCart);
    }
}
