package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.CategoryRepository;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.RegisterCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;
import com.miguelsperle.nexbuy.module.product.utils.CategoryBuilderTest;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RegisterCategoryUseCaseTest {
    @InjectMocks
    private RegisterCategoryUseCaseImpl registerCategoryUseCase;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Should register category")
    public void should_register_category() {
        final Category category = CategoryBuilderTest.create();

        Mockito.when(this.categoryRepository.existsByName(Mockito.any())).thenReturn(false);

        Mockito.when(this.categoryRepository.save(Mockito.any())).thenReturn(category);

        final RegisterCategoryUseCaseInput registerCategoryUseCaseInput = RegisterCategoryUseCaseInput.with(category.getName());

        this.registerCategoryUseCase.execute(registerCategoryUseCaseInput);

        Mockito.verify(this.categoryRepository, Mockito.times(1)).existsByName(Mockito.any());
        Mockito.verify(this.categoryRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Should throw DomainException when category with same name already exists")
    public void should_throw_DomainException_when_category_with_same_name_already_exists() {
        final Category category = CategoryBuilderTest.create();

        Mockito.when(this.categoryRepository.existsByName(Mockito.any())).thenReturn(true);

        final RegisterCategoryUseCaseInput registerCategoryUseCaseInput = RegisterCategoryUseCaseInput.with(category.getName());

        final DomainException exception = Assertions.assertThrows(DomainException.class, () ->
            this.registerCategoryUseCase.execute(registerCategoryUseCaseInput)
        );

        final String expectedErrorMessage = "Category with this name already exists";

        Assertions.assertInstanceOf(DomainException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.categoryRepository, Mockito.times(1)).existsByName(Mockito.any());
    }
}
