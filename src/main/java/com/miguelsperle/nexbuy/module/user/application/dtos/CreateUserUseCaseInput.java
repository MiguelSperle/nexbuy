package com.miguelsperle.nexbuy.module.user.application.dtos;

import com.miguelsperle.nexbuy.module.user.domain.enums.UserType;
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
    private UserType userType;
    private String cpf;
    private String generalRegister;
    private String cnpj;
    private String legalName;
    private String stateRegistration;
}
