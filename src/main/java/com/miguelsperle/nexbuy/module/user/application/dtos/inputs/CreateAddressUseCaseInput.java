package com.miguelsperle.nexbuy.module.user.application.dtos.inputs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAddressUseCaseInput {
    private String addressLine;
    private String addressNumber;
    private String zipCode;
    private String neighborhood;
    private String city;
    private String uf;
    private String complement;
}
