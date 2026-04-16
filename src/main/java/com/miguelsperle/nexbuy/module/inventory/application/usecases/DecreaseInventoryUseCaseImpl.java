package com.miguelsperle.nexbuy.module.inventory.application.usecases;

import com.miguelsperle.nexbuy.module.inventory.application.abstractions.repositories.InventoryMovementRepository;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.InventoryMovement;
import com.miguelsperle.nexbuy.shared.application.abstractions.wrapper.TransactionManager;
import com.miguelsperle.nexbuy.module.inventory.application.abstractions.usecases.DecreaseInventoryUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.abstractions.repositories.InventoryRepository;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.DecreaseInventoryUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.Inventory;
import com.miguelsperle.nexbuy.module.inventory.domain.enums.InventoryMovementType;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

public class DecreaseInventoryUseCaseImpl implements DecreaseInventoryUseCase {
    private final InventoryRepository inventoryRepository;
    private final InventoryMovementRepository inventoryMovementRepository;
    private final TransactionManager transactionManager;

    public DecreaseInventoryUseCaseImpl(
            InventoryRepository inventoryRepository,
            InventoryMovementRepository inventoryMovementRepository,
            TransactionManager transactionManager
    ) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryMovementRepository = inventoryMovementRepository;
        this.transactionManager = transactionManager;
    }

    @Override
    public void execute(DecreaseInventoryUseCaseInput decreaseInventoryUseCaseInput) {
        final Inventory inventory = this.getInventoryBySku(decreaseInventoryUseCaseInput.sku());

        if (inventory.getQuantity() < decreaseInventoryUseCaseInput.quantity()) {
            throw DomainException.with("The quantity in inventory is insufficient", 409);
        }

        final int decreasedInventoryQuantity = inventory.getQuantity() - decreaseInventoryUseCaseInput.quantity();

        final Inventory updatedInventory = inventory.withQuantity(decreasedInventoryQuantity);

        this.transactionManager.runTransaction(() -> {
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
