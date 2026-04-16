package com.miguelsperle.nexbuy.module.inventory.infrastructure.configurations.usecases;

import com.miguelsperle.nexbuy.module.inventory.application.abstractions.usecases.GetInventoryMovementsUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.abstractions.repositories.InventoryMovementRepository;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.GetInventoryMovementsUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InventoryMovementUseCasesConfiguration {
    @Bean
    public GetInventoryMovementsUseCase getInventoryMovementsUseCase(InventoryMovementRepository inventoryRepository) {
        return new GetInventoryMovementsUseCaseImpl(inventoryRepository);
    }
}
