package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.BrandRepository;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetBrandsUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetBrandsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;
import com.miguelsperle.nexbuy.module.product.utils.BrandBuilderTest;
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
public class GetBrandsUseCaseTest {
    @InjectMocks
    private GetBrandsUseCaseImpl getBrandsUseCase;

    @Mock
    private BrandRepository brandRepository;

    @Test
    @DisplayName("Should get brands")
    public void should_get_brands() {
        final Brand brand = BrandBuilderTest.create();

        final SearchQuery searchQuery = SearchQuery.newSearchQuery(1, 10, "name", "asc");

        final PaginationMetadata paginationMetadata = new PaginationMetadata(
                searchQuery.page(), searchQuery.perPage(), 1, List.of(brand).size()
        );

        final Pagination<Brand> paginatedBrands = new Pagination<>(
                paginationMetadata, List.of(brand)
        );

        Mockito.when(this.brandRepository.findAllPaginated(Mockito.any())).thenReturn(paginatedBrands);

        final GetBrandsUseCaseInput getBrandsUseCaseInput = GetBrandsUseCaseInput.with(
                searchQuery
        );

        final GetBrandsUseCaseOutput getBrandsUseCaseOutput = this.getBrandsUseCase.execute(getBrandsUseCaseInput);

        Assertions.assertNotNull(getBrandsUseCaseOutput);
        Assertions.assertNotNull(getBrandsUseCaseOutput.paginatedBrands());

        Assertions.assertEquals(paginatedBrands, getBrandsUseCaseOutput.paginatedBrands());
        Assertions.assertEquals(1, getBrandsUseCaseOutput.paginatedBrands().items().size());
        Assertions.assertEquals(paginationMetadata, getBrandsUseCaseOutput.paginatedBrands().paginationMetadata());

        Mockito.verify(this.brandRepository, Mockito.times(1)).findAllPaginated(Mockito.any());
    }
}
