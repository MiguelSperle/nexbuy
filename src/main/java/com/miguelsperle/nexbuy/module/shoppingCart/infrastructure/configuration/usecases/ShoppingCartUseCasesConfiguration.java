package com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.in.usecases.AddItemToShoppingCartUseCase;
import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.out.persistence.ShoppingCartItemRepository;
import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.out.persistence.ShoppingCartRepository;
import com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.AddItemToShoppingCartUseCaseImpl;
import com.miguelsperle.nexbuy.shared.application.ports.out.services.SecurityContextService;
import com.miguelsperle.nexbuy.shared.application.ports.out.transaction.TransactionExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShoppingCartUseCasesConfiguration {
    @Bean
    public AddItemToShoppingCartUseCase addItemToShoppingCartUseCase(
            ShoppingCartRepository shoppingCartRepository,
            ShoppingCartItemRepository shoppingCartItemRepository,
            SecurityContextService securityContextService,
            TransactionExecutor transactionExecutor
    ) {
        return new AddItemToShoppingCartUseCaseImpl(
                shoppingCartRepository,
                shoppingCartItemRepository,
                securityContextService,
                transactionExecutor
        );
    }
}
