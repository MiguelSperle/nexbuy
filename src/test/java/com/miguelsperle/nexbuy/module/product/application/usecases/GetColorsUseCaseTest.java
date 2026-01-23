package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ColorRepository;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetColorsUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetColorsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.domain.entities.Color;
import com.miguelsperle.nexbuy.module.product.utils.ColorBuilderTest;
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
public class GetColorsUseCaseTest {
    @InjectMocks
    private GetColorsUseCaseImpl getColorsUseCase;

    @Mock
    private ColorRepository colorRepository;

    @Test
    @DisplayName("Should get colors")
    public void should_get_colors() {
        final Color color = ColorBuilderTest.create();

        final SearchQuery searchQuery = SearchQuery.newSearchQuery(1, 10, "name", "asc");

        final PaginationMetadata paginationMetadata = new PaginationMetadata(
                searchQuery.page(), searchQuery.perPage(), 1, List.of(color).size()
        );

        final Pagination<Color> paginatedColors = new Pagination<>(
                paginationMetadata, List.of(color)
        );

        Mockito.when(this.colorRepository.findAllPaginated(Mockito.any())).thenReturn(paginatedColors);

        final GetColorsUseCaseInput getColorsUseCaseInput = GetColorsUseCaseInput.with(
                searchQuery
        );

        final GetColorsUseCaseOutput getColorsUseCaseOutput = this.getColorsUseCase.execute(getColorsUseCaseInput);

        Assertions.assertNotNull(getColorsUseCaseOutput);
        Assertions.assertNotNull(getColorsUseCaseOutput.paginatedColors());

        Assertions.assertEquals(paginatedColors, getColorsUseCaseOutput.paginatedColors());
        Assertions.assertEquals(1, getColorsUseCaseOutput.paginatedColors().items().size());
        Assertions.assertEquals(paginationMetadata, getColorsUseCaseOutput.paginatedColors().paginationMetadata());

        Mockito.verify(this.colorRepository, Mockito.times(1)).findAllPaginated(Mockito.any());
    }
}
