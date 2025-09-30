package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.CategoryRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ProductRepository;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.DeleteCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;
import com.miguelsperle.nexbuy.module.product.utils.CategoryBuilderTest;
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
public class DeleteCategoryUseCaseTest {
    @InjectMocks
    private DeleteCategoryUseCaseImpl deleteCategoryUseCase;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("Should delete category")
    public void should_delete_category() {
        final Category category = CategoryBuilderTest.create();

        Mockito.when(this.categoryRepository.findById(Mockito.any())).thenReturn(Optional.of(category));

        Mockito.when(this.productRepository.existsByCategoryId(Mockito.any())).thenReturn(false);

        Mockito.doNothing().when(this.categoryRepository).deleteById(Mockito.any());

        final DeleteCategoryUseCaseInput deleteCategoryUseCaseInput = DeleteCategoryUseCaseInput.with(category.getId());

        this.deleteCategoryUseCase.execute(deleteCategoryUseCaseInput);

        Mockito.verify(this.categoryRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.productRepository, Mockito.times(1)).existsByCategoryId(Mockito.any());
        Mockito.verify(this.categoryRepository, Mockito.times(1)).deleteById(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when category does not exist")
    public void should_throw_NotFoundException_when_category_does_not_exist() {
        final Category category = CategoryBuilderTest.create();

        Mockito.when(this.categoryRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final DeleteCategoryUseCaseInput deleteCategoryUseCaseInput = DeleteCategoryUseCaseInput.with(category.getId());

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
            this.deleteCategoryUseCase.execute(deleteCategoryUseCaseInput)
        );

        final String expectedErrorMessage = "Category not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.categoryRepository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    @DisplayName("Should throw DomainException when trying to delete category with associated products")
    public void should_throw_DomainException_when_trying_to_delete_category_with_associated_products() {
        final Category category = CategoryBuilderTest.create();

        Mockito.when(this.categoryRepository.findById(Mockito.any())).thenReturn(Optional.of(category));

        Mockito.when(this.productRepository.existsByCategoryId(Mockito.any())).thenReturn(true);

        final DeleteCategoryUseCaseInput deleteCategoryUseCaseInput = DeleteCategoryUseCaseInput.with(category.getId());

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.deleteCategoryUseCase.execute(deleteCategoryUseCaseInput)
        );

        final String expectedErrorMessage = "Category cannot be deleted because it is already associated with products";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.categoryRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.productRepository, Mockito.times(1)).existsByCategoryId(Mockito.any());
    }
}
