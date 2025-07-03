package com.miguelsperle.nexbuy.module.product.application.dtos.inputs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBrandStatusUseCaseInput {
    private String brandId;
    private String brandStatus;
}
