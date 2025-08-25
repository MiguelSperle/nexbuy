package com.miguelsperle.nexbuy.module.shoppingCart.application.usecases;

import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.in.usecases.AddItemToShoppingCartUseCase;
import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.out.persistence.ShoppingCartItemRepository;
import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.out.persistence.ShoppingCartRepository;
import com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs.AddItemToShoppingCartUseCaseInput;
import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCart;
import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCartItem;
import com.miguelsperle.nexbuy.shared.application.ports.out.transaction.TransactionExecutor;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class AddItemToShoppingCartUseCaseImpl implements AddItemToShoppingCartUseCase {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartItemRepository shoppingCartItemRepository;
    private final TransactionExecutor transactionExecutor;

    public AddItemToShoppingCartUseCaseImpl(
            ShoppingCartRepository shoppingCartRepository,
            ShoppingCartItemRepository shoppingCartItemRepository,
            TransactionExecutor transactionExecutor
    ) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartItemRepository = shoppingCartItemRepository;
        this.transactionExecutor = transactionExecutor;
    }

    @Override
    public void execute(AddItemToShoppingCartUseCaseInput addItemToShoppingCartUseCaseInput) {
        final ShoppingCart shoppingCart = this.getShoppingCartById(addItemToShoppingCartUseCaseInput.shoppingCartId());

        final Optional<ShoppingCartItem> shoppingCartItem = this.getShoppingCartItemByShoppingCartIdAndProductId(shoppingCart.getId(), addItemToShoppingCartUseCaseInput.productId());

        this.transactionExecutor.runTransaction(() -> {
            if (shoppingCartItem.isPresent()) {
                final int newQuantity = shoppingCartItem.get().getQuantity() + 1;
                final ShoppingCartItem updatedShoppingCartItem = shoppingCartItem.get().withQuantity(newQuantity);
                this.saveShoppingCartItem(updatedShoppingCartItem);
            } else {
                final ShoppingCartItem newShoppingCartItem = ShoppingCartItem.newShoppingCartItem(
                        shoppingCart.getId(),
                        addItemToShoppingCartUseCaseInput.productId(),
                        addItemToShoppingCartUseCaseInput.unitPrice()
                );
                this.saveShoppingCartItem(newShoppingCartItem);
            }

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

    private void saveShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCartRepository.save(shoppingCart);
    }

    private Optional<ShoppingCartItem> getShoppingCartItemByShoppingCartIdAndProductId(String shoppingCartId, String productId) {
        return this.shoppingCartItemRepository.findByShoppingCartIdAndProductId(shoppingCartId, productId);
    }

    private void saveShoppingCartItem(ShoppingCartItem shoppingCartItem) {
        this.shoppingCartItemRepository.save(shoppingCartItem);
    }

    private List<ShoppingCartItem> getAllShoppingCartItemsByShoppingCartId(String shoppingCartId) {
        return this.shoppingCartItemRepository.findAllByShoppingCartId(shoppingCartId);
    }
}
