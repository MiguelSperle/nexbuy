package com.miguelsperle.nexbuy.module.order.application.usecases.io.inputs.complement;

public record DeliveryComplementInput(
        AddressComplementInput address,
        FreightComplementInput freight
) {
    public static DeliveryComplementInput with(
            AddressComplementInput address,
            FreightComplementInput freight
    ) {
        return new DeliveryComplementInput(address, freight);
    }
}
