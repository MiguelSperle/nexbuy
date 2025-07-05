package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.GetAddressUseCaseOutput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAddressResponse {
    private String id;
    private String addressLine;
    private String addressNumber;
    private String zipCode;
    private String neighborhood;
    private String city;
    private String uf;
    private String complement;

    public static GetAddressResponse fromOutput(GetAddressUseCaseOutput getAddressUseCaseOutput) {
        return new GetAddressResponse(
                getAddressUseCaseOutput.getAddress().getId(),
                getAddressUseCaseOutput.getAddress().getAddressLine(),
                getAddressUseCaseOutput.getAddress().getAddressNumber(),
                getAddressUseCaseOutput.getAddress().getZipCode(),
                getAddressUseCaseOutput.getAddress().getNeighborhood(),
                getAddressUseCaseOutput.getAddress().getCity(),
                getAddressUseCaseOutput.getAddress().getUf(),
                getAddressUseCaseOutput.getAddress().getComplement()
        );
    }
}
