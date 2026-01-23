package com.miguelsperle.nexbuy.module.inventory.application.usecases;

import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.InventoryRepository;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.GetInventoryUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs.GetInventoryUseCaseOutput;
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
public class GetInventoryUseCaseTest {
    @InjectMocks
    private GetInventoryUseCaseImpl getInventoryUseCase;

    @Mock
    private InventoryRepository inventoryRepository;

    @Test
    @DisplayName("Should get inventory")
    public void should_get_inventory() {
        final Inventory inventory = InventoryBuilderTest.create();

        Mockito.when(this.inventoryRepository.findBySku(Mockito.any())).thenReturn(Optional.of(inventory));

        final GetInventoryUseCaseInput getInventoryUseCaseInput = GetInventoryUseCaseInput.with(inventory.getSku());

        final GetInventoryUseCaseOutput getInventoryUseCaseOutput = this.getInventoryUseCase.execute(getInventoryUseCaseInput);

        Assertions.assertNotNull(getInventoryUseCaseOutput);
        Assertions.assertNotNull(getInventoryUseCaseOutput.inventory());

        Assertions.assertEquals(inventory, getInventoryUseCaseOutput.inventory());

        Mockito.verify(this.inventoryRepository, Mockito.times(1)).findBySku(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when inventory does not exist")
    public void should_throw_NotFoundException_when_inventory_does_not_exist() {
        final Inventory inventory = InventoryBuilderTest.create();

        Mockito.when(this.inventoryRepository.findBySku(Mockito.any())).thenReturn(Optional.empty());

        final GetInventoryUseCaseInput getInventoryUseCaseInput = GetInventoryUseCaseInput.with(inventory.getSku());

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.getInventoryUseCase.execute(getInventoryUseCaseInput)
        );

        final String expectedErrorMessage = "Inventory not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.inventoryRepository, Mockito.times(1)).findBySku(Mockito.any());
    }
}
