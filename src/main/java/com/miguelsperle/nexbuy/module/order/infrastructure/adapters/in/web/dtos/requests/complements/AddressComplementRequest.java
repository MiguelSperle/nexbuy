package com.miguelsperle.nexbuy.module.order.infrastructure.adapters.in.web.dtos.requests.complements;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddressComplementRequest(
        @NotBlank(message = "Address line should not be neither null nor blank")
        @Size(max = 100, message = "Address line should not exceed 100 characters")
        String addressLine,

        @NotBlank(message = "Address number should not be neither null nor blank")
        @Size(max = 15, message = "Address number should not exceed 15 characters")
        String addressNumber,

        @NotBlank(message = "Zip code should not be neither null nor blank")
        @Pattern(regexp = "^$|^\\d{5}-\\d{3}$", message = "Zip code should be in the format XXXXX-XXX")
        String zipCode,

        @NotBlank(message = "Neighborhood should not be neither null nor blank")
        @Size(max = 40, message = "Neighborhood should not exceed 40 characters")
        String neighborhood,

        @NotBlank(message = "City should not be neither null nor blank")
        @Size(max = 40, message = "City should not exceed 40 characters")
        String city,

        @NotBlank(message = "Uf should not be neither null nor blank")
        @Pattern(regexp = "^$|^[A-Z]{2}$", message = "Uf must contain exactly 2 uppercase letters")
        String uf,

        @Size(max = 100, message = "Complement should not exceed 100 characters")
        String complement
) {
    public static AddressComplementRequest with(
            String addressLine,
            String addressNumber,
            String zipCode,
            String neighborhood,
            String city,
            String uf,
            String complement
    ) {
        return new AddressComplementRequest(
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
