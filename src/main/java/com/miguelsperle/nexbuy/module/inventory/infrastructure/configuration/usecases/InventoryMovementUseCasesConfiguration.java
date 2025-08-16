package com.miguelsperle.nexbuy.module.inventory.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.module.inventory.application.ports.in.usecases.CreateInventoryMovementUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.ports.in.usecases.GetInventoryMovementsUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.InventoryMovementRepository;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.CreateInventoryMovementUseCaseImpl;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.GetInventoryMovementsUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InventoryMovementUseCasesConfiguration {
    @Bean
    public GetInventoryMovementsUseCase getInventoryMovementsUseCase(InventoryMovementRepository inventoryRepository) {
        return new GetInventoryMovementsUseCaseImpl(inventoryRepository);
    }

    @Bean
    public CreateInventoryMovementUseCase createInventoryMovementUseCase(InventoryMovementRepository inventoryMovementRepository) {
        return new CreateInventoryMovementUseCaseImpl(inventoryMovementRepository);
    }
}
