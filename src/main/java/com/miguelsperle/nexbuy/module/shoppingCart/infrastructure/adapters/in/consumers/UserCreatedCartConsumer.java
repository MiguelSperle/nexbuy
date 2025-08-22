package com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.in.consumers;

import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.out.persistence.ShoppingCartRepository;
import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCart;
import com.miguelsperle.nexbuy.shared.domain.events.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCreatedCartConsumer {
    private final ShoppingCartRepository shoppingCartRepository;

    private static final String USER_CREATED_CART_QUEUE = "user.created.cart.queue";

    @RabbitListener(queues = USER_CREATED_CART_QUEUE)
    public void onMessage(UserCreatedEvent userCreatedEvent) {
        final ShoppingCart newShoppingCart = ShoppingCart.newShoppingCart(userCreatedEvent.id());

        this.saveShoppingCart(newShoppingCart);
    }

    private void saveShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCartRepository.save(shoppingCart);
    }
}
