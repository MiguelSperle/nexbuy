package com.miguelsperle.nexbuy.module.order.infrastructure.rest.dtos.res.complements;

public record OrderDeliveryComplementResponse(
        String addressLine,
        String addressNumber,
        String zipCode,
        String neighborhood,
        String city,
        String uf,
        String complement
) {
    public static OrderDeliveryComplementResponse from(
            String addressLine,
            String addressNumber,
            String zipCode,
            String neighborhood,
            String city,
            String uf,
            String complement
    ) {
        return new OrderDeliveryComplementResponse(
                addressLine,
                addressNumber,
                zipCode,
                neighborhood,
                city,
                uf,
                complement
        );
    }
}
