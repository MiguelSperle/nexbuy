package com.miguelsperle.nexbuy.module.inventory.application.usecases;

import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.InventoryRepository;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.GetInventoriesUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs.GetInventoriesUseCaseOutput;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.Inventory;
import com.miguelsperle.nexbuy.module.inventory.utils.InventoryBuilderTest;
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
public class GetInventoriesUseCaseTest {
    @InjectMocks
    private GetInventoriesUseCaseImpl getInventoriesUseCase;

    @Mock
    private InventoryRepository inventoryRepository;

    @Test
    @DisplayName("Should get inventories")
    public void should_get_inventories() {
        final Inventory inventory = InventoryBuilderTest.create();

        final SearchQuery searchQuery = SearchQuery.newSearchQuery(1, 10, "quantity", "asc");

        final PaginationMetadata paginationMetadata = new PaginationMetadata(
                searchQuery.page(), searchQuery.perPage(), 1, List.of(inventory).size()
        );

        final Pagination<Inventory> paginatedInventories = new Pagination<>(
                paginationMetadata, List.of(inventory)
        );

        Mockito.when(this.inventoryRepository.findAllPaginated(Mockito.any())).thenReturn(paginatedInventories);

        final GetInventoriesUseCaseInput getInventoriesUseCaseInput = GetInventoriesUseCaseInput.with(searchQuery);

        final GetInventoriesUseCaseOutput getInventoriesUseCaseOutput = this.getInventoriesUseCase.execute(getInventoriesUseCaseInput);

        Assertions.assertNotNull(getInventoriesUseCaseOutput);
        Assertions.assertNotNull(getInventoriesUseCaseOutput.paginatedInventories());

        Assertions.assertEquals(paginatedInventories, getInventoriesUseCaseOutput.paginatedInventories());
        Assertions.assertEquals(1, getInventoriesUseCaseOutput.paginatedInventories().items().size());
        Assertions.assertEquals(paginationMetadata, getInventoriesUseCaseOutput.paginatedInventories().paginationMetadata());

        Mockito.verify(this.inventoryRepository, Mockito.times(1)).findAllPaginated(Mockito.any());
    }
}
