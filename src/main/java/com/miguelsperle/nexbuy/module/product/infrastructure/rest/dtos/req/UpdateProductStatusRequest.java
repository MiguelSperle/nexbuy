package com.miguelsperle.nexbuy.module.product.infrastructure.rest.dtos.req;

import com.miguelsperle.nexbuy.shared.infrastructure.configurations.annotations.ValidEnum;
import com.miguelsperle.nexbuy.module.product.domain.enums.ProductStatus;

public record UpdateProductStatusRequest(
        @ValidEnum(enumClass = ProductStatus.class, message = "Product status should be either ACTIVE or INACTIVE")
        String productStatus
) {
}
