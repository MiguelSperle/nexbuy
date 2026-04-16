package com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.configurations.usecases;

import com.miguelsperle.nexbuy.module.shoppingCart.application.abstractions.usecases.*;
import com.miguelsperle.nexbuy.module.shoppingCart.application.abstractions.repositories.ShoppingCartItemRepository;
import com.miguelsperle.nexbuy.module.shoppingCart.application.abstractions.repositories.ShoppingCartRepository;
import com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.*;
import com.miguelsperle.nexbuy.shared.application.abstractions.services.SecurityContextService;
import com.miguelsperle.nexbuy.shared.application.abstractions.wrapper.TransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShoppingCartUseCasesConfiguration {
    @Bean
    public AddItemToShoppingCartUseCase addItemToShoppingCartUseCase(
            ShoppingCartRepository shoppingCartRepository,
            ShoppingCartItemRepository shoppingCartItemRepository,
            TransactionManager transactionManager
    ) {
        return new AddItemToShoppingCartUseCaseImpl(
                shoppingCartRepository,
                shoppingCartItemRepository,
                transactionManager
        );
    }

    @Bean
    public UpdateShoppingCartItemUseCase updateShoppingCartItemUseCase(
            ShoppingCartRepository shoppingCartRepository,
            ShoppingCartItemRepository shoppingCartItemRepository,
            TransactionManager transactionManager
    ) {
        return new UpdateShoppingCartItemUseCaseImpl(
                shoppingCartRepository,
                shoppingCartItemRepository,
                transactionManager
        );
    }

    @Bean
    public GetShoppingCartUseCase getShoppingCartUseCase(
            ShoppingCartRepository shoppingCartRepository,
            ShoppingCartItemRepository shoppingCartItemRepository,
            SecurityContextService securityContextService
    ) {
        return new GetShoppingCartUseCaseImpl(
                shoppingCartRepository,
                shoppingCartItemRepository,
                securityContextService
        );
    }

    @Bean
    public DeleteShoppingCartItemUseCase deleteShoppingCartItemUseCase(
            ShoppingCartRepository shoppingCartRepository,
            ShoppingCartItemRepository shoppingCartItemRepository,
            TransactionManager transactionManager
    ) {
        return new DeleteShoppingCartItemUseCaseImpl(
                shoppingCartRepository,
                shoppingCartItemRepository,
                transactionManager
        );
    }

    @Bean
    public DeleteAllShoppingCartItemsUseCase deleteAllShoppingCartItemsUseCase(
            ShoppingCartRepository shoppingCartRepository,
            ShoppingCartItemRepository shoppingCartItemRepository,
            TransactionManager transactionManager
    ) {
        return new DeleteAllShoppingCartItemsUseCaseImpl(
                shoppingCartRepository,
                shoppingCartItemRepository,
                transactionManager
        );
    }
}
