package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.CategoryRepository;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetCategoriesUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetCategoriesUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;
import com.miguelsperle.nexbuy.module.product.utils.CategoryBuilderTest;
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
public class GetCategoriesUseCaseTest {
    @InjectMocks
    private GetCategoriesUseCaseImpl getCategoriesUseCase;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Should get categories")
    public void should_get_categories() {
        final Category category = CategoryBuilderTest.create();

        final SearchQuery searchQuery = SearchQuery.newSearchQuery(1, 10, "name", "asc");

        final PaginationMetadata paginationMetadata = new PaginationMetadata(
                searchQuery.page(), searchQuery.perPage(), 1, List.of(category).size()
        );

        final Pagination<Category> paginatedCategories = new Pagination<>(
                paginationMetadata, List.of(category)
        );

        Mockito.when(this.categoryRepository.findAllPaginated(Mockito.any())).thenReturn(paginatedCategories);

        final GetCategoriesUseCaseInput getCategoriesUseCaseInput = GetCategoriesUseCaseInput.with(
                searchQuery
        );

        final GetCategoriesUseCaseOutput getCategoriesUseCaseOutput = this.getCategoriesUseCase.execute(getCategoriesUseCaseInput);

        Assertions.assertNotNull(getCategoriesUseCaseOutput);
        Assertions.assertNotNull(getCategoriesUseCaseOutput.paginatedCategories());

        Assertions.assertEquals(paginatedCategories, getCategoriesUseCaseOutput.paginatedCategories());
        Assertions.assertEquals(1, getCategoriesUseCaseOutput.paginatedCategories().items().size());
        Assertions.assertEquals(paginationMetadata, getCategoriesUseCaseOutput.paginatedCategories().paginationMetadata());

        Mockito.verify(this.categoryRepository, Mockito.times(1)).findAllPaginated(Mockito.any());
    }
}
