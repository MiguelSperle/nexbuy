package com.miguelsperle.nexbuy.module.inventory.application.usecases;

import com.miguelsperle.nexbuy.module.inventory.application.ports.in.IGetInventoryUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.IInventoryRepository;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.GetInventoryUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs.GetInventoryUseCaseOutput;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.Inventory;
import com.miguelsperle.nexbuy.module.inventory.domain.exceptions.InventoryNotFoundException;

public class GetInventoryUseCase implements IGetInventoryUseCase {
    private final IInventoryRepository inventoryRepository;

    public GetInventoryUseCase(IInventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public GetInventoryUseCaseOutput execute(GetInventoryUseCaseInput getInventoryUseCaseInput) {
        final Inventory inventory = this.getInventoryBySku(getInventoryUseCaseInput.sku());

        return GetInventoryUseCaseOutput.from(inventory);
    }

    private Inventory getInventoryBySku(String sku) {
        return this.inventoryRepository.findBySku(sku)
                .orElseThrow(() -> InventoryNotFoundException.with("Inventory not found"));
    }
}
