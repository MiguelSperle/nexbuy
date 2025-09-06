package com.miguelsperle.nexbuy.module.freight.application.usecases.io.inputs;

import com.miguelsperle.nexbuy.module.freight.application.usecases.io.inputs.complement.ItemsComplementInput;

import java.util.List;

public record ConsultFreightQuotesUseCaseInput(
        String toCep,
        List<ItemsComplementInput> itemsComplementInput
) {
    public static ConsultFreightQuotesUseCaseInput with(String toCep, List<ItemsComplementInput> itemsComplementInput) {
        return new ConsultFreightQuotesUseCaseInput(toCep, itemsComplementInput);
    }
}
