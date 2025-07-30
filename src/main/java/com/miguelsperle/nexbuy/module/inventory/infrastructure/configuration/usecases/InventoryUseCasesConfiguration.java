package com.miguelsperle.nexbuy.module.inventory.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.core.application.ports.out.transaction.ITransactionExecutor;
import com.miguelsperle.nexbuy.module.inventory.application.ports.in.ICreateInventoryUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.ports.in.IDecreaseInventoryUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.ports.in.IGetInventoryUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.ports.in.IIncreaseInventoryUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.IInventoryMovementRepository;
import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.IInventoryRepository;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.CreateInventoryUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.DecreaseInventoryUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.GetInventoryUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.IncreaseInventoryUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InventoryUseCasesConfiguration {
    @Bean
    public ICreateInventoryUseCase createInventoryUseCase(IInventoryRepository inventoryRepository) {
        return new CreateInventoryUseCase(inventoryRepository);
    }

    @Bean
    public IIncreaseInventoryUseCase increaseInventoryUseCase(
            IInventoryRepository inventoryRepository,
            IInventoryMovementRepository inventoryMovementRepository,
            ITransactionExecutor transactionExecutor
    ) {
        return new IncreaseInventoryUseCase(
                inventoryRepository,
                inventoryMovementRepository,
                transactionExecutor
        );
    }

    @Bean
    public IDecreaseInventoryUseCase decreaseInventoryUseCase(
            IInventoryRepository inventoryRepository,
            IInventoryMovementRepository inventoryMovementRepository,
            ITransactionExecutor transactionExecutor
    ) {
        return new DecreaseInventoryUseCase(
                inventoryRepository,
                inventoryMovementRepository,
                transactionExecutor
        );
    }

    @Bean
    public IGetInventoryUseCase getInventoryUseCase(IInventoryRepository inventoryRepository) {
        return new GetInventoryUseCase(inventoryRepository);
    }
}
