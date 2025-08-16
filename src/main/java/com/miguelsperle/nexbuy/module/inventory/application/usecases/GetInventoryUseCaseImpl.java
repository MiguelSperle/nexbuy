package com.miguelsperle.nexbuy.module.inventory.application.usecases;

import com.miguelsperle.nexbuy.module.inventory.application.ports.in.usecases.GetInventoryUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.InventoryRepository;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.GetInventoryUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs.GetInventoryUseCaseOutput;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.Inventory;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

public class GetInventoryUseCaseImpl implements GetInventoryUseCase {
    private final InventoryRepository inventoryRepository;

    public GetInventoryUseCaseImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public GetInventoryUseCaseOutput execute(GetInventoryUseCaseInput getInventoryUseCaseInput) {
        final Inventory inventory = this.getInventoryBySku(getInventoryUseCaseInput.sku());

        return GetInventoryUseCaseOutput.from(inventory);
    }

    private Inventory getInventoryBySku(String sku) {
        return this.inventoryRepository.findBySku(sku)
                .orElseThrow(() -> NotFoundException.with("Inventory not found"));
    }
}
