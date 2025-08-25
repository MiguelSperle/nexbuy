package com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.in.usecases.AddToShoppingCartUseCase;
import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.in.usecases.DeleteShoppingCartItemUseCase;
import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.in.usecases.GetShoppingCartUseCase;
import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.in.usecases.UpdateShoppingCartItemUseCase;
import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.out.persistence.ShoppingCartItemRepository;
import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.out.persistence.ShoppingCartRepository;
import com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.AddToShoppingCartUseCaseImpl;
import com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.DeleteShoppingCartItemUseCaseImpl;
import com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.GetShoppingCartUseCaseImpl;
import com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.UpdateShoppingCartItemUseCaseImpl;
import com.miguelsperle.nexbuy.shared.application.ports.out.transaction.TransactionExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShoppingCartUseCasesConfiguration {
    @Bean
    public AddToShoppingCartUseCase addToShoppingCartUseCase(
            ShoppingCartRepository shoppingCartRepository,
            ShoppingCartItemRepository shoppingCartItemRepository,
            TransactionExecutor transactionExecutor
    ) {
        return new AddToShoppingCartUseCaseImpl(
                shoppingCartRepository,
                shoppingCartItemRepository,
                transactionExecutor
        );
    }

    @Bean
    public UpdateShoppingCartItemUseCase updateShoppingCartItemUseCase(
            ShoppingCartRepository shoppingCartRepository,
            ShoppingCartItemRepository shoppingCartItemRepository,
            TransactionExecutor transactionExecutor
    ) {
        return new UpdateShoppingCartItemUseCaseImpl(
                shoppingCartRepository,
                shoppingCartItemRepository,
                transactionExecutor
        );
    }

    @Bean
    public GetShoppingCartUseCase getShoppingCartUseCase(
            ShoppingCartRepository shoppingCartRepository,
            ShoppingCartItemRepository shoppingCartItemRepository
    ) {
        return new GetShoppingCartUseCaseImpl(
                shoppingCartRepository,
                shoppingCartItemRepository
        );
    }

    @Bean
    public DeleteShoppingCartItemUseCase deleteShoppingCartItemUseCase(
            ShoppingCartRepository shoppingCartRepository,
            ShoppingCartItemRepository shoppingCartItemRepository,
            TransactionExecutor transactionExecutor
    ) {
        return new DeleteShoppingCartItemUseCaseImpl(
                shoppingCartRepository,
                shoppingCartItemRepository,
                transactionExecutor
        );
    }
}
