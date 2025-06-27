package com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetProductBrandsUseCaseOutput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProductBrandsResponse {
    private String id;
    private String name;

    public static List<GetProductBrandsResponse> fromOutput(GetProductBrandsUseCaseOutput getProductBrandsUseCaseOutput) {
        return getProductBrandsUseCaseOutput.getProductBrands().stream().map(productBrand -> new GetProductBrandsResponse(
                productBrand.getId(),
                productBrand.getName()
        )).toList();
    }
}
