package com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.in.consumers;

import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.out.persistence.ShoppingCartItemRepository;
import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.out.persistence.ShoppingCartRepository;
import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCart;
import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCartItem;
import com.miguelsperle.nexbuy.shared.application.ports.out.transaction.TransactionExecutor;
import com.miguelsperle.nexbuy.shared.domain.events.ProductPriceUpdatedEvent;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductPriceUpdatedConsumer {
    private final ShoppingCartItemRepository shoppingCartItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final TransactionExecutor transactionExecutor;

    private static final String PRODUCT_PRICE_UPDATED_QUEUE = "product.price.updated.queue";

    @RabbitListener(queues = PRODUCT_PRICE_UPDATED_QUEUE)
    public void onMessage(ProductPriceUpdatedEvent productPriceUpdatedEvent) {
        final List<ShoppingCartItem> shoppingCartItems = this.getAllShoppingCartItemsByProductId(productPriceUpdatedEvent.id());

        final List<ShoppingCartItem> updatedShoppingCartItems = shoppingCartItems.stream().map(shoppingCartItem ->
                shoppingCartItem.withUnitPrice(productPriceUpdatedEvent.price())).toList();

        this.transactionExecutor.runTransaction(() -> {
            final List<ShoppingCartItem> savedShoppingCartItems = this.saveAllShoppingCartItems(updatedShoppingCartItems);

            final Set<String> shoppingCartIds = savedShoppingCartItems.stream().map(ShoppingCartItem::getShoppingCartId).collect(Collectors.toSet());

            for (String shoppingCartId : shoppingCartIds) {
                final ShoppingCart shoppingCart = this.getShoppingCartById(shoppingCartId);

                final List<ShoppingCartItem> cartItems = this.getAllShoppingCartItemsByShoppingCartId(shoppingCart.getId());
                final BigDecimal recalculatedTotalAmount = cartItems.stream().map(shoppingCartItem -> shoppingCartItem.getUnitPrice().multiply(BigDecimal.valueOf(shoppingCartItem.getQuantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                final ShoppingCart updatedShoppingCart = shoppingCart.withTotalAmount(recalculatedTotalAmount);
                this.saveShoppingCart(updatedShoppingCart);
            }
        });
    }

    private List<ShoppingCartItem> getAllShoppingCartItemsByProductId(String productId) {
        return this.shoppingCartItemRepository.findAllByProductId(productId);
    }

    private List<ShoppingCartItem> saveAllShoppingCartItems(List<ShoppingCartItem> shoppingCartItems) {
        return this.shoppingCartItemRepository.saveAll(shoppingCartItems);
    }

    private ShoppingCart getShoppingCartById(String id) {
        return this.shoppingCartRepository.findById(id).orElseThrow(() -> NotFoundException.with("Shopping cart not found"));
    }

    private List<ShoppingCartItem> getAllShoppingCartItemsByShoppingCartId(String shoppingCartId) {
        return this.shoppingCartItemRepository.findAllByShoppingCartId(shoppingCartId);
    }

    private void saveShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCartRepository.save(shoppingCart);
    }
}
