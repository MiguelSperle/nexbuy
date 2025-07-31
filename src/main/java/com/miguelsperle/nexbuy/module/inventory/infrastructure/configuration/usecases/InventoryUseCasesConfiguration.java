package com.miguelsperle.nexbuy.module.inventory.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.core.application.ports.out.transaction.TransactionExecutor;
import com.miguelsperle.nexbuy.module.inventory.application.ports.in.*;
import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.InventoryMovementRepository;
import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.InventoryRepository;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InventoryUseCasesConfiguration {
    @Bean
    public CreateInventoryUseCase createInventoryUseCase(InventoryRepository inventoryRepository) {
        return new CreateInventoryUseCaseImpl(inventoryRepository);
    }

    @Bean
    public IncreaseInventoryUseCase increaseInventoryUseCase(
            InventoryRepository inventoryRepository,
            InventoryMovementRepository inventoryMovementRepository,
            TransactionExecutor transactionExecutor
    ) {
        return new IncreaseInventoryUseCaseImpl(
                inventoryRepository,
                inventoryMovementRepository,
                transactionExecutor
        );
    }

    @Bean
    public DecreaseInventoryUseCase decreaseInventoryUseCase(
            InventoryRepository inventoryRepository,
            InventoryMovementRepository inventoryMovementRepository,
            TransactionExecutor transactionExecutor
    ) {
        return new DecreaseInventoryUseCaseImpl(
                inventoryRepository,
                inventoryMovementRepository,
                transactionExecutor
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

    @Bean
    public GetInventoryMovementsUseCase getInventoryMovementsUseCase(InventoryMovementRepository inventoryRepository) {
        return new GetInventoryMovementsUseCaseImpl(inventoryRepository);
    }
}
