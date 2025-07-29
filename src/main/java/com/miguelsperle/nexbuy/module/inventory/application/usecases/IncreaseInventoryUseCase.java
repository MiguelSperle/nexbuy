package com.miguelsperle.nexbuy.module.inventory.application.usecases;

import com.miguelsperle.nexbuy.core.application.ports.out.transaction.ITransactionExecutor;
import com.miguelsperle.nexbuy.module.inventory.application.ports.in.IIncreaseInventoryUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.IInventoryMovementRepository;
import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.IInventoryRepository;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.IncreaseInventoryUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.Inventory;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.InventoryMovement;
import com.miguelsperle.nexbuy.module.inventory.domain.enums.InventoryMovementType;
import com.miguelsperle.nexbuy.module.inventory.domain.exceptions.InventoryNotFoundException;

public class IncreaseInventoryUseCase implements IIncreaseInventoryUseCase {
    private final IInventoryRepository inventoryRepository;
    private final IInventoryMovementRepository inventoryMovementRepository;
    private final ITransactionExecutor transactionExecutor;

    public IncreaseInventoryUseCase(
            IInventoryRepository inventoryRepository,
            IInventoryMovementRepository inventoryMovementRepository,
            ITransactionExecutor transactionExecutor
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
