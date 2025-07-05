package com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetBrandUseCaseOutput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBrandResponse {
    private String id;
    private String name;

    public static GetBrandResponse fromOutput(GetBrandUseCaseOutput getBrandUseCaseOutput) {
        return new GetBrandResponse(getBrandUseCaseOutput.getBrand().getId(), getBrandUseCaseOutput.getBrand().getName());
    }
}
