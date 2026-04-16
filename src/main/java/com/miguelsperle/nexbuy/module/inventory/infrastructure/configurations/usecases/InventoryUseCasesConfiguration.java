package com.miguelsperle.nexbuy.module.inventory.infrastructure.configurations.usecases;

import com.miguelsperle.nexbuy.module.inventory.application.abstractions.usecases.*;
import com.miguelsperle.nexbuy.module.inventory.application.abstractions.repositories.InventoryMovementRepository;
import com.miguelsperle.nexbuy.shared.application.abstractions.wrapper.TransactionManager;
import com.miguelsperle.nexbuy.module.inventory.application.abstractions.repositories.InventoryRepository;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InventoryUseCasesConfiguration {
    @Bean
    public IncreaseInventoryUseCase increaseInventoryUseCase(
            InventoryRepository inventoryRepository,
            InventoryMovementRepository inventoryMovementRepository,
            TransactionManager transactionManager
    ) {
        return new IncreaseInventoryUseCaseImpl(
                inventoryRepository,
                inventoryMovementRepository,
                transactionManager
        );
    }

    @Bean
    public DecreaseInventoryUseCase decreaseInventoryUseCase(
            InventoryRepository inventoryRepository,
            InventoryMovementRepository inventoryMovementRepository,
            TransactionManager transactionManager
    ) {
        return new DecreaseInventoryUseCaseImpl(
                inventoryRepository,
                inventoryMovementRepository,
                transactionManager
        );
    }

    @Bean
    public GetInventoriesUseCase getInventoriesUseCase(InventoryRepository inventoryRepository) {
        return new GetInventoriesUseCaseImpl(inventoryRepository);
    }

    @Bean
    public GetInventoryUseCase getInventoryUseCase(InventoryRepository inventoryRepository) {
        return new GetInventoryUseCaseImpl(inventoryRepository);
    }

    @Bean
    public CheckInventoryAvailabilityUseCase checkInventoryAvailabilityUseCase(InventoryRepository inventoryRepository) {
        return new CheckInventoryAvailabilityUseCaseImpl(inventoryRepository);
    }
}
