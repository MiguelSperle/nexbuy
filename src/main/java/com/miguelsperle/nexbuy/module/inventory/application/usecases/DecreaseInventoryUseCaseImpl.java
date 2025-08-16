package com.miguelsperle.nexbuy.module.inventory.application.usecases;

import com.miguelsperle.nexbuy.shared.application.ports.out.transaction.TransactionExecutor;
import com.miguelsperle.nexbuy.module.inventory.application.ports.in.usecases.CreateInventoryMovementUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.ports.in.usecases.DecreaseInventoryUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.InventoryRepository;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.CreateInventoryMovementUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.DecreaseInventoryUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.Inventory;
import com.miguelsperle.nexbuy.module.inventory.domain.enums.InventoryMovementType;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

public class DecreaseInventoryUseCaseImpl implements DecreaseInventoryUseCase {
    private final InventoryRepository inventoryRepository;
    private final CreateInventoryMovementUseCase createInventoryMovementUseCase;
    private final TransactionExecutor transactionExecutor;

    public DecreaseInventoryUseCaseImpl(
            InventoryRepository inventoryRepository,
            CreateInventoryMovementUseCase createInventoryMovementUseCase,
            TransactionExecutor transactionExecutor
    ) {
        this.inventoryRepository = inventoryRepository;
        this.createInventoryMovementUseCase = createInventoryMovementUseCase;
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

            this.createInventoryMovementUseCase.execute(CreateInventoryMovementUseCaseInput.with(
                    savedInventory.getId(),
                    savedInventory.getSku(),
                    decreaseInventoryUseCaseInput.quantity(),
                    InventoryMovementType.OUT
            ));
        });
    }

    private Inventory getInventoryBySku(String sku) {
        return this.inventoryRepository.findBySku(sku)
                .orElseThrow(() -> NotFoundException.with("Inventory not found"));
    }

    private Inventory saveInventory(Inventory inventory) {
        return this.inventoryRepository.save(inventory);
    }
}
