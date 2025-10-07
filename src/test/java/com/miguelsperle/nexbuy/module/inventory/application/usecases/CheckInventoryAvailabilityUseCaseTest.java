package com.miguelsperle.nexbuy.module.inventory.application.usecases;

import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.InventoryRepository;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.CheckInventoryAvailabilityUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs.CheckInventoryAvailabilityUseCaseOutput;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.Inventory;
import com.miguelsperle.nexbuy.module.inventory.utils.InventoryBuilderTest;
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
public class CheckInventoryAvailabilityUseCaseTest {
    @InjectMocks
    private CheckInventoryAvailabilityUseCaseImpl checkInventoryAvailabilityUseCase;

    @Mock
    private InventoryRepository inventoryRepository;

    @Test
    @DisplayName("Should check inventory availability with positive quantity")
    public void should_check_inventory_availability_with_positive_quantity() {
        final Inventory inventory = InventoryBuilderTest.create();

        Mockito.when(this.inventoryRepository.findBySku(Mockito.any())).thenReturn(Optional.of(inventory));

        final CheckInventoryAvailabilityUseCaseOutput checkInventoryAvailabilityUseCaseOutput = this.checkInventoryAvailabilityUseCase.execute(CheckInventoryAvailabilityUseCaseInput.with(
                inventory.getSku()
        ));

        Assertions.assertNotNull(checkInventoryAvailabilityUseCaseOutput);
        Assertions.assertNotNull(checkInventoryAvailabilityUseCaseOutput.isAvailable());

        Assertions.assertEquals(true, checkInventoryAvailabilityUseCaseOutput.isAvailable());

        Mockito.verify(this.inventoryRepository, Mockito.times(1)).findBySku(Mockito.any());
    }

    @Test
    @DisplayName("Should check inventory availability with negative quantity")
    public void should_check_inventory_availability_with_negative_quantity() {
        final Inventory inventory = InventoryBuilderTest.create().withQuantity(0);

        Mockito.when(this.inventoryRepository.findBySku(Mockito.any())).thenReturn(Optional.of(inventory));

        final CheckInventoryAvailabilityUseCaseOutput checkInventoryAvailabilityUseCaseOutput = this.checkInventoryAvailabilityUseCase.execute(CheckInventoryAvailabilityUseCaseInput.with(
                inventory.getSku()
        ));

        Assertions.assertNotNull(checkInventoryAvailabilityUseCaseOutput);
        Assertions.assertNotNull(checkInventoryAvailabilityUseCaseOutput.isAvailable());

        Assertions.assertEquals(false, checkInventoryAvailabilityUseCaseOutput.isAvailable());

        Mockito.verify(this.inventoryRepository, Mockito.times(1)).findBySku(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when inventory does not exist")
    public void should_throw_NotFoundException_when_inventory_does_not_exist() {
        final Inventory inventory = InventoryBuilderTest.create();

        Mockito.when(this.inventoryRepository.findBySku(Mockito.any())).thenReturn(Optional.empty());

        final CheckInventoryAvailabilityUseCaseInput checkInventoryAvailabilityUseCaseInput = CheckInventoryAvailabilityUseCaseInput.with(
                inventory.getSku()
        );

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
            this.checkInventoryAvailabilityUseCase.execute(checkInventoryAvailabilityUseCaseInput)
        );

        final String expectedErrorMessage = "Inventory not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.inventoryRepository, Mockito.times(1)).findBySku(Mockito.any());
    }
}
