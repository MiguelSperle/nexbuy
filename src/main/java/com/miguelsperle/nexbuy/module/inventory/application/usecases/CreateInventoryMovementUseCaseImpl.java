package com.miguelsperle.nexbuy.module.inventory.application.usecases;

import com.miguelsperle.nexbuy.module.inventory.application.ports.in.CreateInventoryMovementUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.InventoryMovementRepository;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.CreateInventoryMovementUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.InventoryMovement;

public class CreateInventoryMovementUseCaseImpl implements CreateInventoryMovementUseCase {
    private final InventoryMovementRepository inventoryMovementRepository;

    public CreateInventoryMovementUseCaseImpl(InventoryMovementRepository inventoryMovementRepository) {
        this.inventoryMovementRepository = inventoryMovementRepository;
    }

    @Override
    public void execute(CreateInventoryMovementUseCaseInput createInventoryMovementUseCaseInput) {
        final InventoryMovement newInventoryMovement = InventoryMovement.newInventoryMovement(
                createInventoryMovementUseCaseInput.inventoryId(),
                createInventoryMovementUseCaseInput.sku(),
                createInventoryMovementUseCaseInput.quantity(),
                createInventoryMovementUseCaseInput.movementType()
        );

        this.saveInventoryMovement(newInventoryMovement);
    }

    private void saveInventoryMovement(InventoryMovement inventoryMovement) {
        this.inventoryMovementRepository.save(inventoryMovement);
    }
}
