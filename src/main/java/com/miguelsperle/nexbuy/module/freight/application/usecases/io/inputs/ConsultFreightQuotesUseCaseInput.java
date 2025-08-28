package com.miguelsperle.nexbuy.module.freight.application.usecases.io.inputs;

import com.miguelsperle.nexbuy.module.freight.application.usecases.io.inputs.complement.ProductsComplementInput;

import java.util.List;

public record ConsultFreightQuotesUseCaseInput(
        String toCep,
        List<ProductsComplementInput> productsComplementInput
) {
    public static ConsultFreightQuotesUseCaseInput with(String toCep, List<ProductsComplementInput> productsComplementInput) {
        return new ConsultFreightQuotesUseCaseInput(toCep, productsComplementInput);
    }
}
