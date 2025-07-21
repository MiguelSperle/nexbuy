package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.DeleteCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.exceptions.CategoryAssociatedWithProductsException;
import com.miguelsperle.nexbuy.module.product.application.exceptions.CategoryNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IDeleteCategoryUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.ICategoryGateway;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IProductGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;

public class DeleteCategoryUseCase implements IDeleteCategoryUseCase {
    private final ICategoryGateway categoryGateway;
    private final IProductGateway productGateway;

    public DeleteCategoryUseCase(ICategoryGateway categoryGateway, IProductGateway productGateway) {
        this.categoryGateway = categoryGateway;
        this.productGateway = productGateway;
    }

    @Override
    public void execute(DeleteCategoryUseCaseInput deleteCategoryUseCaseInput) {
        final Category category = this.getCategoryById(deleteCategoryUseCaseInput.categoryId());

        if (this.verifyProductAlreadyExistsByCategoryId(category.getId())) {
            throw new CategoryAssociatedWithProductsException("Category cannot be deleted because it is already associated with products");
        }

        this.deleteCategoryById(category.getId());
    }

    private Category getCategoryById(String categoryId) {
        return this.categoryGateway.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }

    private boolean verifyProductAlreadyExistsByCategoryId(String categoryId) {
        return this.productGateway.existsByCategoryId(categoryId);
    }

    private void deleteCategoryById(String categoryId) {
        this.categoryGateway.deleteById(categoryId);
    }
}
