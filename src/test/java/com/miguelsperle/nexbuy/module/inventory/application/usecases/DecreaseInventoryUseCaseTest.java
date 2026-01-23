package com.miguelsperle.nexbuy.module.inventory.application.usecases;

import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.InventoryMovementRepository;
import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.InventoryRepository;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.DecreaseInventoryUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.Inventory;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.InventoryMovement;
import com.miguelsperle.nexbuy.module.inventory.domain.enums.InventoryMovementType;
import com.miguelsperle.nexbuy.module.inventory.utils.InventoryBuilderTest;
import com.miguelsperle.nexbuy.module.inventory.utils.InventoryMovementBuilderTest;
import com.miguelsperle.nexbuy.shared.application.ports.out.transaction.TransactionExecutor;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DecreaseInventoryUseCaseTest {
    @InjectMocks
    private DecreaseInventoryUseCaseImpl decreaseInventoryUseCase;

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private InventoryMovementRepository inventoryMovementRepository;

    @Mock
    private TransactionExecutor transactionExecutor;

    @Test
    @DisplayName("Should decrease inventory")
    public void should_decrease_inventory() {
        final Inventory inventory = InventoryBuilderTest.create();
        final InventoryMovement inventoryMovement = InventoryMovementBuilderTest.create(inventory.getId(), InventoryMovementType.OUT);

        Mockito.when(this.inventoryRepository.findBySku(Mockito.any())).thenReturn(Optional.of(inventory));

        Mockito.doAnswer(invocationOnMock -> {
            final Runnable runnable = invocationOnMock.getArgument(0);
            runnable.run();
            return runnable;
        }).when(this.transactionExecutor).runTransaction(Mockito.any());

        final DecreaseInventoryUseCaseInput decreaseInventoryUseCaseInput = DecreaseInventoryUseCaseInput.with(
                inventory.getSku(),
                inventory.getQuantity()
        );

        final Inventory updatedInventory = inventory.withQuantity(
                inventory.getQuantity() - decreaseInventoryUseCaseInput.quantity()
        );

        Mockito.when(this.inventoryRepository.save(Mockito.any())).thenReturn(updatedInventory);

        Mockito.when(this.inventoryMovementRepository.save(Mockito.any())).thenReturn(inventoryMovement);

        this.decreaseInventoryUseCase.execute(decreaseInventoryUseCaseInput);

        Mockito.verify(this.inventoryRepository, Mockito.times(1)).findBySku(Mockito.any());
        Mockito.verify(this.transactionExecutor, Mockito.times(1)).runTransaction(Mockito.any());
        Mockito.verify(this.inventoryRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.inventoryMovementRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when inventory does not exist")
    public void should_throw_NotFoundException_when_inventory_does_not_exist() {
        final Inventory inventory = InventoryBuilderTest.create();

        Mockito.when(this.inventoryRepository.findBySku(Mockito.any())).thenReturn(Optional.empty());

        final DecreaseInventoryUseCaseInput decreaseInventoryUseCaseInput = DecreaseInventoryUseCaseInput.with(
                inventory.getSku(),
                inventory.getQuantity()
        );

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
            this.decreaseInventoryUseCase.execute(decreaseInventoryUseCaseInput)
        );

        final String expectedErrorMessage = "Inventory not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.inventoryRepository, Mockito.times(1)).findBySku(Mockito.any());
    }

    @Test
    @DisplayName("Should throw DomainException when inventory quantity is insufficient")
    public void should_throw_DomainException_when_inventory_quantity_is_insufficient() {
        final Inventory inventory = InventoryBuilderTest.create().withQuantity(0);

        Mockito.when(this.inventoryRepository.findBySku(Mockito.any())).thenReturn(Optional.of(inventory));

        final DecreaseInventoryUseCaseInput decreaseInventoryUseCaseInput = DecreaseInventoryUseCaseInput.with(
                inventory.getSku(),
                1
        );

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.decreaseInventoryUseCase.execute(decreaseInventoryUseCaseInput)
        );

        final String expectedErrorMessage = "The quantity in inventory is insufficient";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.inventoryRepository, Mockito.times(1)).findBySku(Mockito.any());
    }
}
