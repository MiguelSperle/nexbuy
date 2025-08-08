package com.miguelsperle.nexbuy.module.inventory.application.usecases;

import com.miguelsperle.nexbuy.shared.application.ports.out.transaction.TransactionExecutor;
import com.miguelsperle.nexbuy.module.inventory.application.ports.in.CreateInventoryMovementUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.ports.in.IncreaseInventoryUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.InventoryRepository;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.CreateInventoryMovementUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.IncreaseInventoryUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.Inventory;
import com.miguelsperle.nexbuy.module.inventory.domain.enums.InventoryMovementType;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

public class IncreaseInventoryUseCaseImpl implements IncreaseInventoryUseCase {
    private final InventoryRepository inventoryRepository;
    private final CreateInventoryMovementUseCase createInventoryMovementUseCase;
    private final TransactionExecutor transactionExecutor;

    public IncreaseInventoryUseCaseImpl(
            InventoryRepository inventoryRepository,
            CreateInventoryMovementUseCase createInventoryMovementUseCase,
            TransactionExecutor transactionExecutor
    ) {
        this.inventoryRepository = inventoryRepository;
        this.createInventoryMovementUseCase = createInventoryMovementUseCase;
        this.transactionExecutor = transactionExecutor;
    }

    @Override
    public void execute(IncreaseInventoryUseCaseInput increaseInventoryUseCaseInput) {
        final Inventory inventory = this.getInventoryBySku(increaseInventoryUseCaseInput.sku());

        final int increasedInventoryQuantity = inventory.getQuantity() + increaseInventoryUseCaseInput.quantity();

        final Inventory updatedInventory = inventory.withQuantity(increasedInventoryQuantity);

        this.transactionExecutor.runTransaction(() -> {
            final Inventory savedInventory = this.saveInventory(updatedInventory);

            this.createInventoryMovementUseCase.execute(CreateInventoryMovementUseCaseInput.with(
                    savedInventory.getId(),
                    savedInventory.getSku(),
                    increaseInventoryUseCaseInput.quantity(),
                    InventoryMovementType.IN
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
