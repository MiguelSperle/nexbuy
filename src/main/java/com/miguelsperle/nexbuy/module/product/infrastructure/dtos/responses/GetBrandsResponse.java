package com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetBrandsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.domain.enums.BrandStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBrandsResponse {
    private String id;
    private String name;
    private BrandStatus brandStatus;

    public static List<GetBrandsResponse> fromOutput(GetBrandsUseCaseOutput getBrandsUseCaseOutput) {
        return getBrandsUseCaseOutput.getBrands().stream().map(productBrand -> new GetBrandsResponse(
                productBrand.getId(),
                productBrand.getName(),
                productBrand.getBrandStatus()
        )).toList();
    }
}
