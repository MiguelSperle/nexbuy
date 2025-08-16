package com.miguelsperle.nexbuy.module.inventory.application.usecases;

import com.miguelsperle.nexbuy.module.inventory.application.ports.in.usecases.CheckInventoryAvailabilityUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.InventoryRepository;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.CheckInventoryAvailabilityUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs.CheckInventoryAvailabilityUseCaseOutput;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.Inventory;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

public class CheckInventoryAvailabilityUseCaseImpl implements CheckInventoryAvailabilityUseCase {
    private final InventoryRepository inventoryRepository;

    public CheckInventoryAvailabilityUseCaseImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public CheckInventoryAvailabilityUseCaseOutput execute(CheckInventoryAvailabilityUseCaseInput checkInventoryAvailabilityUseCaseInput) {
        final Inventory inventory = this.getInventoryBySku(checkInventoryAvailabilityUseCaseInput.sku());

        final boolean isAvailable = inventory.getQuantity() > 0;

        return CheckInventoryAvailabilityUseCaseOutput.from(isAvailable);
    }

    private Inventory getInventoryBySku(String sku) {
        return this.inventoryRepository.findBySku(sku)
                .orElseThrow(() -> NotFoundException.with("Inventory not found"));
    }
}
