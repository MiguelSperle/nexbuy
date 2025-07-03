package com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests;

import com.miguelsperle.nexbuy.core.infrastructure.annotations.ValidEnum;
import com.miguelsperle.nexbuy.module.product.domain.enums.BrandStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBrandRequest {
    @NotBlank(message = "Name should not be neither null nor blank")
    @Size(max = 50, message = "Name should not exceed 50 characters")
    private String name;

    @ValidEnum(enumClass = BrandStatus.class, message = "Status should be either ACTIVE or INACTIVE")
    private String status;
}
