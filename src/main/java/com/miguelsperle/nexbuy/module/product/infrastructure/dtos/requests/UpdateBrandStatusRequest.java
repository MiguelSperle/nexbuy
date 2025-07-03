package com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests;

import com.miguelsperle.nexbuy.core.infrastructure.annotations.ValidEnum;
import com.miguelsperle.nexbuy.module.product.domain.enums.BrandStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBrandStatusRequest {
    @ValidEnum(enumClass = BrandStatus.class, message = "Brand status should be either ACTIVE or INACTIVE")
    private String brandStatus;
}
