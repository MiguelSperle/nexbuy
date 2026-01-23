package com.miguelsperle.nexbuy.module.inventory.application.usecases;

import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.InventoryMovementRepository;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.InventoryMovement;
import com.miguelsperle.nexbuy.shared.application.ports.out.transaction.TransactionExecutor;
import com.miguelsperle.nexbuy.module.inventory.application.ports.in.usecases.DecreaseInventoryUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.InventoryRepository;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.DecreaseInventoryUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.Inventory;
import com.miguelsperle.nexbuy.module.inventory.domain.enums.InventoryMovementType;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

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
            throw DomainException.with("The quantity in inventory is insufficient", 409);
        }

        final int decreasedInventoryQuantity = inventory.getQuantity() - decreaseInventoryUseCaseInput.quantity();

        final Inventory updatedInventory = inventory.withQuantity(decreasedInventoryQuantity);

        this.transactionExecutor.runTransaction(() -> {
            final Inventory savedInventory = this.saveInventory(updatedInventory);

            final InventoryMovement newInventoryMovement = InventoryMovement.newInventoryMovement(
                    savedInventory.getId(),
                    savedInventory.getSku(),
                    decreaseInventoryUseCaseInput.quantity(),
                    InventoryMovementType.OUT
            );

            this.saveInventoryMovement(newInventoryMovement);
        });
    }

    private Inventory getInventoryBySku(String sku) {
        return this.inventoryRepository.findBySku(sku)
                .orElseThrow(() -> NotFoundException.with("Inventory not found"));
    }

    private Inventory saveInventory(Inventory inventory) {
        return this.inventoryRepository.save(inventory);
    }

    private void saveInventoryMovement(InventoryMovement inventoryMovement) {
        this.inventoryMovementRepository.save(inventoryMovement);
    }
}
