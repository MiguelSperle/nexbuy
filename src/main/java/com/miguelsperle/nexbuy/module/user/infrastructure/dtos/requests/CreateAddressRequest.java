package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAddressRequest {
    @NotBlank(message = "Address line should not be neither null nor empty")
    @Size(max = 100, message = "Address line should not exceed 100 characters")
    private String addressLine;

    @NotBlank(message = "Address number should not be neither null nor empty")
    @Size(max = 15, message = "Address number should not exceed 15 characters")
    private String addressNumber;

    @NotBlank(message = "Zip code should not be neither null nor empty")
    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "Zip code should be in the format XXXXX-XXX")
    private String zipCode;

    @NotBlank(message = "Neighborhood should not be neither null nor empty")
    @Size(max = 40, message = "Neighborhood should not exceed 40 characters")
    private String neighborhood;

    @NotBlank(message = "City should not be neither null nor empty")
    @Size(max = 40, message = "City should not exceed 40 characters")
    private String city;

    @NotBlank(message = "Uf should not be neither null nor empty")
    @Pattern(regexp = "^[A-Z]{2}$", message = "Uf must contain exactly 2 uppercase letters")
    private String uf;

    @Size(max = 100, message = "Complement should not exceed 100 characters")
    private String complement;
}
