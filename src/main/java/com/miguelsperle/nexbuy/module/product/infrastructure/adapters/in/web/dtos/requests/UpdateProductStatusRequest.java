package com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.dtos.requests;

import com.miguelsperle.nexbuy.core.infrastructure.annotations.ValidEnum;
import com.miguelsperle.nexbuy.module.product.domain.enums.ProductStatus;

public record UpdateProductStatusRequest(
        @ValidEnum(enumClass = ProductStatus.class, message = "Product status should be either ACTIVE or INACTIVE")
        String productStatus
) {
}
