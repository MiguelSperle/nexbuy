package com.miguelsperle.nexbuy.module.shoppingCart.application.usecases;

import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.in.usecases.UpdateShoppingCartItemUseCase;
import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.out.persistence.ShoppingCartItemRepository;
import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.out.persistence.ShoppingCartRepository;
import com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs.UpdateShoppingCartUseCaseInput;
import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCart;
import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCartItem;
import com.miguelsperle.nexbuy.module.shoppingCart.domain.enums.ShoppingCartItemAction;
import com.miguelsperle.nexbuy.shared.application.ports.out.services.SecurityContextService;
import com.miguelsperle.nexbuy.shared.application.ports.out.transaction.TransactionExecutor;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

import java.math.BigDecimal;
import java.util.List;

public class UpdateShoppingCartItemUseCaseImpl implements UpdateShoppingCartItemUseCase {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartItemRepository shoppingCartItemRepository;
    private final SecurityContextService securityContextService;
    private final TransactionExecutor transactionExecutor;

    public UpdateShoppingCartItemUseCaseImpl(
            ShoppingCartRepository shoppingCartRepository,
            ShoppingCartItemRepository shoppingCartItemRepository,
            SecurityContextService securityContextService,
            TransactionExecutor transactionExecutor
    ) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartItemRepository = shoppingCartItemRepository;
        this.securityContextService = securityContextService;
        this.transactionExecutor = transactionExecutor;
    }

    @Override
    public void execute(UpdateShoppingCartUseCaseInput updateShoppingCartUseCaseInput) {
        final ShoppingCartItemAction convertedToShoppingCartItemAction = ShoppingCartItemAction.valueOf(updateShoppingCartUseCaseInput.action());

        final String authenticatedUserId = this.getAuthenticatedUserId();

        final ShoppingCart shoppingCart = this.getShoppingCartByUserId(authenticatedUserId);

        final ShoppingCartItem shoppingCartItem = this.getShoppingCartItemById(updateShoppingCartUseCaseInput.itemId());

        this.transactionExecutor.runTransaction(() -> {
            if (convertedToShoppingCartItemAction == ShoppingCartItemAction.ADD) {
                final int newQuantity = shoppingCartItem.getQuantity() + updateShoppingCartUseCaseInput.quantity();
                this.updateShoppingCartItem(shoppingCartItem, newQuantity);
            } else {
                if (updateShoppingCartUseCaseInput.quantity() > shoppingCartItem.getQuantity()) {
                    throw DomainException.with("The quantity to be removed cannot be greater than the quantity of the item in the cart", 400);
                }

                final int newQuantity = shoppingCartItem.getQuantity() - updateShoppingCartUseCaseInput.quantity();

                if (newQuantity == 0) {
                    this.deleteShoppingCartItem(shoppingCartItem.getId());
                } else {
                    this.updateShoppingCartItem(shoppingCartItem, newQuantity);
                }
            }

            final List<ShoppingCartItem> shoppingCartItems = this.getAllShoppingCartItemsByShoppingCartId(shoppingCart.getId());
            final BigDecimal calculatedTotalAmount = shoppingCartItems.stream().map(shoppingCartItemInList -> shoppingCartItemInList.getUnitPrice().multiply(BigDecimal.valueOf(shoppingCartItemInList.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            final ShoppingCart updatedShoppingCart = shoppingCart.withTotalAmount(calculatedTotalAmount);
            this.saveShoppingCart(updatedShoppingCart);
        });
    }

    private String getAuthenticatedUserId() {
        return this.securityContextService.getAuthenticatedUserId();
    }

    private ShoppingCart getShoppingCartByUserId(String userId) {
        return this.shoppingCartRepository.findByUserId(userId).orElseThrow(() -> NotFoundException.with("Shopping cart not found"));
    }

    private ShoppingCartItem getShoppingCartItemById(String id) {
        return this.shoppingCartItemRepository.findById(id).orElseThrow(() -> NotFoundException.with("Shopping cart item not found"));
    }

    private void deleteShoppingCartItem(String id) {
        this.shoppingCartItemRepository.deleteById(id);
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

    private void updateShoppingCartItem(ShoppingCartItem shoppingCartItem, int newQuantity) {
        final ShoppingCartItem updatedShoppingCartItem = shoppingCartItem.withQuantity(newQuantity);
        this.saveShoppingCartItem(updatedShoppingCartItem);
    }
}
