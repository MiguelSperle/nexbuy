package com.miguelsperle.nexbuy.module.inventory.application.usecases;

import com.miguelsperle.nexbuy.module.inventory.application.ports.in.ICreateInventoryUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.IInventoryRepository;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.CreateInventoryUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.Inventory;

public class CreateInventoryUseCase implements ICreateInventoryUseCase {
    private final IInventoryRepository inventoryRepository;

    public CreateInventoryUseCase(IInventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public void execute(CreateInventoryUseCaseInput createInventoryUseCaseInput) {
        final Inventory newInventory = Inventory.newInventory(
                createInventoryUseCaseInput.productId(),
                createInventoryUseCaseInput.sku()
        );

        this.saveInventory(newInventory);
    }

    private void saveInventory(Inventory inventory) {
        this.inventoryRepository.save(inventory);
    }
}
