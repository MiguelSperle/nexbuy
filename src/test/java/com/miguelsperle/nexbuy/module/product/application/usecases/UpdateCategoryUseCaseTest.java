package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.CategoryRepository;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.UpdateCategoryUseCaseInput;
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
public class UpdateCategoryUseCaseTest {
    @InjectMocks
    private UpdateCategoryUseCaseImpl updateCategoryUseCase;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Should update category")
    public void should_update_category() {
        final Category category = CategoryBuilderTest.create();

        Mockito.when(this.categoryRepository.findById(Mockito.any())).thenReturn(Optional.of(category));

        final UpdateCategoryUseCaseInput updateCategoryUseCaseInput = UpdateCategoryUseCaseInput.with(
                category.getId(),
                category.getName()
        );

        final Category updatedCategory = category.withName(updateCategoryUseCaseInput.name());

        Mockito.when(this.categoryRepository.save(Mockito.any())).thenReturn(updatedCategory);

        this.updateCategoryUseCase.execute(updateCategoryUseCaseInput);

        Mockito.verify(this.categoryRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.categoryRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when category does not exist")
    public void should_throw_NotFoundException_when_category_does_not_exist() {
        final Category category = CategoryBuilderTest.create();

        Mockito.when(this.categoryRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final UpdateCategoryUseCaseInput updateCategoryUseCaseInput = UpdateCategoryUseCaseInput.with(
                category.getId(),
                category.getName()
        );

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.updateCategoryUseCase.execute(updateCategoryUseCaseInput)
        );

        final String expectedErrorMessage = "Category not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.categoryRepository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    @DisplayName("Should throw DomainException when category with same name already exists")
    public void should_throw_DomainException_when_category_with_same_name_already_exists() {
        final Category category = CategoryBuilderTest.create();
        final String categoryNewName = "test-new-category-name";

        Mockito.when(this.categoryRepository.findById(Mockito.any())).thenReturn(Optional.of(category));

        Mockito.when(this.categoryRepository.existsByName(Mockito.any())).thenReturn(true);

        final UpdateCategoryUseCaseInput updateCategoryUseCaseInput = UpdateCategoryUseCaseInput.with(
                category.getId(),
                categoryNewName
        );

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
                this.updateCategoryUseCase.execute(updateCategoryUseCaseInput)
        );

        final String expectedErrorMessage = "Category with this name already exists";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.categoryRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.categoryRepository, Mockito.times(1)).existsByName(Mockito.any());
    }
}
