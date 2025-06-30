package com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCategoryRequest {
    @NotBlank(message = "Name should not be neither null nor blank")
    @Size(max = 50, message = "Name should not exceed 50 characters")
    private String name;

    @NotBlank(message = "Description should not be neither null nor blank")
    @Size(max = 255, message = "Description should not exceed 255 characters")
    private String description;

    private String parentCategoryId; // Optional, can be null if no parent category is specified
}
