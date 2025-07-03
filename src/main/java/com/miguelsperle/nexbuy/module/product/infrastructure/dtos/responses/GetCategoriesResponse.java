package com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetCategoriesUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

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
        return getCategoriesUseCaseOutput.getRootCategories().stream().map(rootCategory ->
                buildCategoryTree(rootCategory, getCategoriesUseCaseOutput.getSubCategories())).toList();
    }

    private static GetCategoriesResponse buildCategoryTree(Category currentCategory, List<Category> subCategories) {
        final List<GetCategoriesResponse> subCategoriesResponse = subCategories.stream().filter(subCategory ->
                Objects.equals(subCategory.getParentCategory().getId(), currentCategory.getId())).map(subCategory ->
                buildCategoryTree(subCategory, subCategories)).toList();

        return new GetCategoriesResponse(
                currentCategory.getId(),
                currentCategory.getName(),
                currentCategory.getDescription(),
                currentCategory.getSlug(),
                currentCategory.getHierarchyLevel(),
                subCategoriesResponse
        );
    }
}
