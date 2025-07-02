package com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetCategoriesUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCategoriesResponse {
    private String id;
    private String name;
    private String description;
    private String slug;
    private Integer hierarchyLevel;
    private List<GetCategoriesResponse> subCategories;

    public static List<GetCategoriesResponse> fromOutput(GetCategoriesUseCaseOutput getCategoriesUseCaseOutput) {
        return getCategoriesUseCaseOutput.getCategories().stream()
                .filter(category -> category.getParentCategory() == null)
                .map(rootCategory -> buildCategoryTree(rootCategory, getCategoriesUseCaseOutput.getCategories()))
                .toList();
    }

    private static GetCategoriesResponse buildCategoryTree(Category parentCategory, List<Category> allCategories) {
        final List<GetCategoriesResponse> subCategories = allCategories.stream()
                .filter(category -> category.getParentCategory() != null && category.getParentCategory().getId().equals(parentCategory.getId()))
                .map(subCategory -> buildCategoryTree(subCategory, allCategories)).toList();

        return new GetCategoriesResponse(
                parentCategory.getId(),
                parentCategory.getName(),
                parentCategory.getDescription(),
                parentCategory.getSlug(),
                parentCategory.getHierarchyLevel(),
                subCategories
        );
    }
}
