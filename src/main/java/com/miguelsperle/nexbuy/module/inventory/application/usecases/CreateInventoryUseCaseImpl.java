package com.miguelsperle.nexbuy.module.inventory.application.usecases;

import com.miguelsperle.nexbuy.module.inventory.application.ports.in.CreateInventoryUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.InventoryRepository;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.CreateInventoryUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.Inventory;

public class CreateInventoryUseCaseImpl implements CreateInventoryUseCase {
    private final InventoryRepository inventoryRepository;

    public CreateInventoryUseCaseImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public void execute(CreateInventoryUseCaseInput createInventoryUseCaseInput) {
        final Inventory newInventory = Inventory.newInventory(createInventoryUseCaseInput.sku());

        this.saveInventory(newInventory);
    }

    private void saveInventory(Inventory inventory) {
        this.inventoryRepository.save(inventory);
    }
}
