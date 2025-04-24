package com.miguelsperle.nexbuy.module.user.application.dtos;

import com.miguelsperle.nexbuy.module.user.application.dtos.complements.JuridicalUserInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.complements.PhysicalUserInput;
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
    private PhysicalUserInput physicalUserInput;
    private JuridicalUserInput juridicalUserInput;
}
