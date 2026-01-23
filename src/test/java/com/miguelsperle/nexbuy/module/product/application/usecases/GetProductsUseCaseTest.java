package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ProductRepository;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetProductsUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetProductsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;
import com.miguelsperle.nexbuy.module.product.domain.entities.Color;
import com.miguelsperle.nexbuy.module.product.domain.entities.Product;
import com.miguelsperle.nexbuy.module.product.domain.enums.ProductStatus;
import com.miguelsperle.nexbuy.module.product.utils.BrandBuilderTest;
import com.miguelsperle.nexbuy.module.product.utils.CategoryBuilderTest;
import com.miguelsperle.nexbuy.module.product.utils.ColorBuilderTest;
import com.miguelsperle.nexbuy.module.product.utils.ProductBuilderTest;
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
public class GetProductsUseCaseTest {
    @InjectMocks
    private GetProductsUseCaseImpl getProductsUseCase;

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("Should get products")
    public void should_get_products() {
        final Category category = CategoryBuilderTest.create();
        final Brand brand = BrandBuilderTest.create();
        final Color color = ColorBuilderTest.create();
        final Product product = ProductBuilderTest.create(
                category.getId(), brand.getId(), color.getId(), ProductStatus.ACTIVE
        );

        final SearchQuery searchQuery = SearchQuery.newSearchQuery(1, 10, "name", "asc");

        final PaginationMetadata paginationMetadata = new PaginationMetadata(
                searchQuery.page(), searchQuery.perPage(), 1, List.of(product).size()
        );

        final Pagination<Product> paginatedProducts = new Pagination<>(
                paginationMetadata, List.of(product)
        );

        Mockito.when(this.productRepository.findAllPaginated(Mockito.any())).thenReturn(paginatedProducts);

        final GetProductsUseCaseInput getProductsUseCaseInput = GetProductsUseCaseInput.with(searchQuery);

        final GetProductsUseCaseOutput getProductsUseCaseOutput = this.getProductsUseCase.execute(getProductsUseCaseInput);

        Assertions.assertNotNull(getProductsUseCaseOutput);
        Assertions.assertNotNull(getProductsUseCaseOutput.paginatedProducts());

        Assertions.assertEquals(paginatedProducts, getProductsUseCaseOutput.paginatedProducts());
        Assertions.assertEquals(1, getProductsUseCaseOutput.paginatedProducts().items().size());
        Assertions.assertEquals(paginationMetadata, getProductsUseCaseOutput.paginatedProducts().paginationMetadata());

        Mockito.verify(this.productRepository, Mockito.times(1)).findAllPaginated(Mockito.any());
    }
}
