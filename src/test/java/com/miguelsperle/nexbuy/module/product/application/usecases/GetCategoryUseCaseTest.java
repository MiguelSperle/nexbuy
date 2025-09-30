package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.CategoryRepository;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetCategoryUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;
import com.miguelsperle.nexbuy.module.product.utils.CategoryBuilderTest;
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
public class GetCategoryUseCaseTest {
    @InjectMocks
    private GetCategoryUseCaseImpl getCategoryUseCase;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Should get category")
    public void should_get_category() {
        final Category category = CategoryBuilderTest.create();

        Mockito.when(this.categoryRepository.findById(Mockito.any())).thenReturn(Optional.of(category));

        final GetCategoryUseCaseInput getCategoryUseCaseInput = GetCategoryUseCaseInput.with(category.getId());

        final GetCategoryUseCaseOutput getCategoryUseCaseOutput = this.getCategoryUseCase.execute(getCategoryUseCaseInput);

        Assertions.assertNotNull(getCategoryUseCaseOutput);
        Assertions.assertNotNull(getCategoryUseCaseOutput.category());

        Assertions.assertEquals(category, getCategoryUseCaseOutput.category());

        Mockito.verify(this.categoryRepository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when category does not exist")
    public void should_throw_NotFoundException_when_category_does_not_exist() {
        final Category category = CategoryBuilderTest.create();

        Mockito.when(this.categoryRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final GetCategoryUseCaseInput getCategoryUseCaseInput = GetCategoryUseCaseInput.with(category.getId());

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.getCategoryUseCase.execute(getCategoryUseCaseInput)
        );

        final String expectedErrorMessage = "Category not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.categoryRepository, Mockito.times(1)).findById(Mockito.any());
    }
}
