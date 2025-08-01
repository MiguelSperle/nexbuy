package com.miguelsperle.nexbuy.module.inventory.application.usecases;

import com.miguelsperle.nexbuy.core.application.ports.out.transaction.TransactionExecutor;
import com.miguelsperle.nexbuy.module.inventory.application.ports.in.DecreaseInventoryUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.InventoryMovementRepository;
import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.InventoryRepository;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.DecreaseInventoryUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.Inventory;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.InventoryMovement;
import com.miguelsperle.nexbuy.module.inventory.domain.enums.InventoryMovementType;
import com.miguelsperle.nexbuy.module.inventory.domain.exceptions.InventoryNotFoundException;
import com.miguelsperle.nexbuy.module.inventory.domain.exceptions.InsufficientInventoryException;

public class DecreaseInventoryUseCaseImpl implements DecreaseInventoryUseCase {
    private final InventoryRepository inventoryRepository;
    private final InventoryMovementRepository inventoryMovementRepository;
    private final TransactionExecutor transactionExecutor;

    public DecreaseInventoryUseCaseImpl(
            InventoryRepository inventoryRepository,
            InventoryMovementRepository inventoryMovementRepository,
            TransactionExecutor transactionExecutor
    ) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryMovementRepository = inventoryMovementRepository;
        this.transactionExecutor = transactionExecutor;
    }

    @Override
    public void execute(DecreaseInventoryUseCaseInput decreaseInventoryUseCaseInput) {
        final Inventory inventory = this.getInventoryBySku(decreaseInventoryUseCaseInput.sku());

        if (inventory.getQuantity() < decreaseInventoryUseCaseInput.quantity()) {
            throw InsufficientInventoryException.with("The quantity in inventory is insufficient");
        }

        final int decreasedInventoryQuantity = inventory.getQuantity() - decreaseInventoryUseCaseInput.quantity();

        final InventoryMovement newInventoryMovement = InventoryMovement.newInventoryMovement(
                inventory.getId(),
                inventory.getSku(),
                decreaseInventoryUseCaseInput.quantity(),
                InventoryMovementType.OUT
        );

        final Inventory updatedInventory = inventory.withQuantity(decreasedInventoryQuantity);

        this.transactionExecutor.runTransaction(() -> {
            this.saveInventory(updatedInventory);
            this.saveInventoryEntry(newInventoryMovement);
        });
    }

    private Inventory getInventoryBySku(String sku) {
        return this.inventoryRepository.findBySku(sku)
                .orElseThrow(() -> InventoryNotFoundException.with("Inventory not found"));
    }

    private void saveInventory(Inventory inventory) {
        this.inventoryRepository.save(inventory);
    }

    private void saveInventoryEntry(InventoryMovement inventoryMovement) {
        this.inventoryMovementRepository.save(inventoryMovement);
    }
}
