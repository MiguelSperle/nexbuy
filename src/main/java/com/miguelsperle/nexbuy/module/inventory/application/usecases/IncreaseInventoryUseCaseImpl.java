package com.miguelsperle.nexbuy.module.inventory.application.usecases;

import com.miguelsperle.nexbuy.core.application.ports.out.transaction.TransactionExecutor;
import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.InventoryMovementRepository;
import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.InventoryRepository;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.IncreaseInventoryUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.Inventory;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.InventoryMovement;
import com.miguelsperle.nexbuy.module.inventory.domain.enums.InventoryMovementType;
import com.miguelsperle.nexbuy.module.inventory.domain.exceptions.InventoryNotFoundException;

public class IncreaseInventoryUseCaseImpl implements com.miguelsperle.nexbuy.module.inventory.application.ports.in.IncreaseInventoryUseCase {
    private final InventoryRepository inventoryRepository;
    private final InventoryMovementRepository inventoryMovementRepository;
    private final TransactionExecutor transactionExecutor;

    public IncreaseInventoryUseCaseImpl(
            InventoryRepository inventoryRepository,
            InventoryMovementRepository inventoryMovementRepository,
            TransactionExecutor transactionExecutor
    ) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryMovementRepository = inventoryMovementRepository;
        this.transactionExecutor = transactionExecutor;
    }

    @Override
    public void execute(IncreaseInventoryUseCaseInput increaseInventoryUseCaseInput) {
        final Inventory inventory = this.getInventoryBySku(increaseInventoryUseCaseInput.sku());

        final int increasedInventoryQuantity = inventory.getQuantity() + increaseInventoryUseCaseInput.quantity();

        final InventoryMovement newInventoryMovement = InventoryMovement.newInventoryMovement(
                inventory.getId(),
                inventory.getSku(),
                increaseInventoryUseCaseInput.quantity(),
                InventoryMovementType.IN
        );

        final Inventory updatedInventory = inventory.withQuantity(increasedInventoryQuantity);

        this.transactionExecutor.runTransaction(() -> {
            this.saveInventory(updatedInventory);
            this.saveInventoryMovement(newInventoryMovement);
        });
    }

    private Inventory getInventoryBySku(String sku) {
        return this.inventoryRepository.findBySku(sku)
                .orElseThrow(() -> InventoryNotFoundException.with("Inventory not found"));
    }

    private void saveInventory(Inventory inventory) {
        this.inventoryRepository.save(inventory);
    }

    private void saveInventoryMovement(InventoryMovement inventoryMovement) {
        this.inventoryMovementRepository.save(inventoryMovement);
    }
}
