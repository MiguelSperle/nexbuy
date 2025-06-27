package com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetCategoriesUseCaseOutput;
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

    public static List<GetCategoriesResponse> fromOutput(GetCategoriesUseCaseOutput getCategoriesUseCaseOutput) {
        return getCategoriesUseCaseOutput.getProductCategories().stream().map(productCategory -> new GetCategoriesResponse(
                productCategory.getId(),
                productCategory.getName()
        )).toList();
    }
}
