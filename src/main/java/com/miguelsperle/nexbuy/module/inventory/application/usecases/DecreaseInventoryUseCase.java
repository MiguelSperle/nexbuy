package com.miguelsperle.nexbuy.module.inventory.application.usecases;

import com.miguelsperle.nexbuy.core.application.ports.out.transaction.ITransactionExecutor;
import com.miguelsperle.nexbuy.module.inventory.application.ports.in.IDecreaseInventoryUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.IInventoryMovementRepository;
import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.IInventoryRepository;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.DecreaseInventoryUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.Inventory;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.InventoryMovement;
import com.miguelsperle.nexbuy.module.inventory.domain.enums.InventoryMovementType;
import com.miguelsperle.nexbuy.module.inventory.domain.exceptions.InventoryNotFoundException;
import com.miguelsperle.nexbuy.module.inventory.domain.exceptions.InventoryInsufficientQuantityException;

public class DecreaseInventoryUseCase implements IDecreaseInventoryUseCase {
    private final IInventoryRepository inventoryRepository;
    private final IInventoryMovementRepository inventoryMovementRepository;
    private final ITransactionExecutor transactionExecutor;

    public DecreaseInventoryUseCase(
            IInventoryRepository inventoryRepository,
            IInventoryMovementRepository inventoryMovementRepository,
            ITransactionExecutor transactionExecutor
    ) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryMovementRepository = inventoryMovementRepository;
        this.transactionExecutor = transactionExecutor;
    }

    @Override
    public void execute(DecreaseInventoryUseCaseInput decreaseInventoryUseCaseInput) {
        final Inventory inventory = this.getInventoryBySku(decreaseInventoryUseCaseInput.sku());

        if (inventory.getQuantity() < decreaseInventoryUseCaseInput.quantity()) {
            throw InventoryInsufficientQuantityException.with("The quantity in inventory is insufficient");
        }

        final int decreasedInventoryQuantity = inventory.getQuantity() - decreaseInventoryUseCaseInput.quantity();

        final InventoryMovement newInventoryMovement = InventoryMovement.newInventoryMovement(
                inventory.getId(),
                decreaseInventoryUseCaseInput.quantity(),
                InventoryMovementType.OUT
        );

        final Inventory updatedInventory = inventory.withQuantity(decreasedInventoryQuantity);

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
