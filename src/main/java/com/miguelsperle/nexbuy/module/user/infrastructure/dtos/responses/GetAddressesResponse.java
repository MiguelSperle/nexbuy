package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.GetAddressesUseCaseOutput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAddressesResponse {
    private String id;
    private String addressLine;
    private String addressNumber;
    private String zipCode;
    private String neighborhood;
    private String city;
    private String uf;
    private String complement;

    public static List<GetAddressesResponse> fromOutput(GetAddressesUseCaseOutput getAddressesUseCaseOutput) {
        return getAddressesUseCaseOutput.getAddresses().stream().map(address -> new GetAddressesResponse(
                address.getId(),
                address.getAddressLine(),
                address.getAddressNumber(),
                address.getZipCode(),
                address.getNeighborhood(),
                address.getCity(),
                address.getUf(),
                address.getComplement()
        )).toList();
    }
}
