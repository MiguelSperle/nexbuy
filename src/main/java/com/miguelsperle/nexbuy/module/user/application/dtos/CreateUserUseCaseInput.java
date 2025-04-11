package com.miguelsperle.nexbuy.module.user.application.dtos;

import com.miguelsperle.nexbuy.module.user.application.dtos.complements.JuridicalUserInputComplement;
import com.miguelsperle.nexbuy.module.user.application.dtos.complements.PhysicalUserInputComplement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserUseCaseInput {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String userType;
    private PhysicalUserInputComplement physicalUserInputComplement;
    private JuridicalUserInputComplement juridicalUserInputComplement;
}
