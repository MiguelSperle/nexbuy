package com.miguelsperle.nexbuy.module.shipping.application.usecases.io.inputs.complement;

public record ProductsComplementInput(
        String id,
        Integer height,
        Integer width,
        Integer length,
        Integer weight,
        Integer quantity
) {
    public static ProductsComplementInput with(
            String id,
            Integer height,
            Integer width,
            Integer length,
            Integer weight,
            Integer quantity
    ) {
        return new ProductsComplementInput(
                id,
                height,
                width,
                length,
                weight,
                quantity
        );
    }
}
