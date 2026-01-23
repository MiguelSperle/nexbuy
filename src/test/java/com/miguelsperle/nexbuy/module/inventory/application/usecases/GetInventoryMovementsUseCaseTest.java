package com.miguelsperle.nexbuy.module.inventory.application.usecases;

import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.InventoryMovementRepository;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.GetInventoryMovementsUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs.GetInventoryMovementsUseCaseOutput;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.Inventory;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.InventoryMovement;
import com.miguelsperle.nexbuy.module.inventory.domain.enums.InventoryMovementType;
import com.miguelsperle.nexbuy.module.inventory.utils.InventoryBuilderTest;
import com.miguelsperle.nexbuy.module.inventory.utils.InventoryMovementBuilderTest;
import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.shared.domain.pagination.PaginationMetadata;
import com.miguelsperle.nexbuy.shared.domain.pagination.SearchQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class GetInventoryMovementsUseCaseTest {
    @InjectMocks
    private GetInventoryMovementsUseCaseImpl getInventoryMovementsUseCase;

    @Mock
    private InventoryMovementRepository inventoryMovementRepository;

    @Test
    @DisplayName("Should get inventory movements")
    public void should_get_inventory_movements() {
        final Inventory inventory = InventoryBuilderTest.create();
        final InventoryMovement inventoryMovement = InventoryMovementBuilderTest.create(inventory.getId(), InventoryMovementType.IN);

        final SearchQuery searchQuery = SearchQuery.newSearchQuery(1, 10, "quantity", "asc");

        final PaginationMetadata paginationMetadata = new PaginationMetadata(
                searchQuery.page(), searchQuery.perPage(), 1, List.of(inventoryMovement).size()
        );

        final Pagination<InventoryMovement> paginatedInventoryMovements = new Pagination<>(
                paginationMetadata, List.of(inventoryMovement)
        );

        Mockito.when(this.inventoryMovementRepository.findAllPaginated(Mockito.any())).thenReturn(paginatedInventoryMovements);

        final GetInventoryMovementsUseCaseInput getInventoryMovementsUseCaseInput = GetInventoryMovementsUseCaseInput.with(searchQuery);

        final GetInventoryMovementsUseCaseOutput getInventoryMovementsUseCaseOutput = this.getInventoryMovementsUseCase.execute(getInventoryMovementsUseCaseInput);

        Assertions.assertNotNull(getInventoryMovementsUseCaseOutput);
        Assertions.assertNotNull(getInventoryMovementsUseCaseOutput.paginatedInventoryMovements());

        Assertions.assertEquals(paginatedInventoryMovements, getInventoryMovementsUseCaseOutput.paginatedInventoryMovements());
        Assertions.assertEquals(1, getInventoryMovementsUseCaseOutput.paginatedInventoryMovements().items().size());
        Assertions.assertEquals(paginationMetadata, getInventoryMovementsUseCaseOutput.paginatedInventoryMovements().paginationMetadata());

        Mockito.verify(this.inventoryMovementRepository, Mockito.times(1)).findAllPaginated(Mockito.any());
    }
}
