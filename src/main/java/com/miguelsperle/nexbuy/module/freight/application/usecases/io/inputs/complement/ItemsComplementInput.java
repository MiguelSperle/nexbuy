package com.miguelsperle.nexbuy.module.freight.application.usecases.io.inputs.complement;

public record ItemsComplementInput(
        String id,
        Integer height,
        Integer width,
        Integer length,
        Integer weight,
        Integer quantity
) {
    public static ItemsComplementInput with(
            String id,
            Integer height,
            Integer width,
            Integer length,
            Integer weight,
            Integer quantity
    ) {
        return new ItemsComplementInput(
                id,
                height,
                width,
                length,
                weight,
                quantity
        );
    }
}
