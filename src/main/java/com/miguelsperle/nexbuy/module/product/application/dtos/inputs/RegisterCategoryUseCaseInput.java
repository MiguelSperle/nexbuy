package com.miguelsperle.nexbuy.module.product.application.dtos.inputs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCategoryUseCaseInput {
    private String name;
    private String description;
    private String parentCategoryId;
}
