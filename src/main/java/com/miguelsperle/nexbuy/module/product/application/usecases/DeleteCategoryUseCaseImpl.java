package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.DeleteCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.domain.exceptions.CategoryAssociatedWithProductsException;
import com.miguelsperle.nexbuy.module.product.domain.exceptions.CategoryNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.ports.in.DeleteCategoryUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.CategoryRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ProductRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;

public class DeleteCategoryUseCaseImpl implements DeleteCategoryUseCase {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public DeleteCategoryUseCaseImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void execute(DeleteCategoryUseCaseInput deleteCategoryUseCaseInput) {
        final Category category = this.getCategoryById(deleteCategoryUseCaseInput.categoryId());

        if (this.verifyProductAlreadyExistsByCategoryId(category.getId())) {
            throw CategoryAssociatedWithProductsException.with("Category cannot be deleted because it is already associated with products");
        }

        this.deleteCategoryById(category.getId());
    }

    private Category getCategoryById(String categoryId) {
        return this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> CategoryNotFoundException.with("Category not found"));
    }

    private boolean verifyProductAlreadyExistsByCategoryId(String categoryId) {
        return this.productRepository.existsByCategoryId(categoryId);
    }

    private void deleteCategoryById(String categoryId) {
        this.categoryRepository.deleteById(categoryId);
    }
}
