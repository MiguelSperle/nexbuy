package com.miguelsperle.nexbuy.module.shoppingCart.application.usecases;

import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.in.usecases.DeleteAllShoppingCartItemsUseCase;
import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.out.persistence.ShoppingCartItemRepository;
import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.out.persistence.ShoppingCartRepository;
import com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs.DeleteAllShoppingCartItemsUseCaseInput;
import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCart;
import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCartItem;
import com.miguelsperle.nexbuy.shared.application.ports.out.transaction.TransactionExecutor;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

import java.math.BigDecimal;
import java.util.List;

public class DeleteAllShoppingCartItemsUseCaseImpl implements DeleteAllShoppingCartItemsUseCase {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartItemRepository shoppingCartItemRepository;
    private final TransactionExecutor transactionExecutor;

    public DeleteAllShoppingCartItemsUseCaseImpl(
            ShoppingCartRepository shoppingCartRepository,
            ShoppingCartItemRepository shoppingCartItemRepository,
            TransactionExecutor transactionExecutor
    ) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartItemRepository = shoppingCartItemRepository;
        this.transactionExecutor = transactionExecutor;
    }

    @Override
    public void execute(DeleteAllShoppingCartItemsUseCaseInput deleteAllShoppingCartItemsUseCaseInput) {
        final ShoppingCart shoppingCart = this.getShoppingCartById(deleteAllShoppingCartItemsUseCaseInput.shoppingCartId());

        final List<ShoppingCartItem> shoppingCartItems = this.getAllShoppingCartItemsByShoppingCartId(shoppingCart.getId());

        this.transactionExecutor.runTransaction(() -> {
            this.deleteAllShoppingCartItems(shoppingCartItems);

            final ShoppingCart updatedShoppingCart = shoppingCart.withTotalAmount(BigDecimal.valueOf(0));
            this.saveShoppingCart(updatedShoppingCart);
        });
    }

    private ShoppingCart getShoppingCartById(String id) {
        return this.shoppingCartRepository.findById(id).orElseThrow(() -> new NotFoundException("Shopping cart not found"));
    }

    private List<ShoppingCartItem> getAllShoppingCartItemsByShoppingCartId(String shoppingCartId) {
        return this.shoppingCartItemRepository.findAllByShoppingCartId(shoppingCartId);
    }

    private void deleteAllShoppingCartItems(List<ShoppingCartItem> shoppingCartItems) {
        this.shoppingCartItemRepository.deleteAll(shoppingCartItems);
    }

    private void saveShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCartRepository.save(shoppingCart);
    }
}
