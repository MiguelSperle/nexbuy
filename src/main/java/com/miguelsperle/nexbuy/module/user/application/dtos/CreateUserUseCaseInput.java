package com.miguelsperle.nexbuy.module.user.application.dtos;

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
    private String cpf;
    private String generalRegister;
    private String cnpj;
    private String fantasyName;
    private String legalName;
    private String stateRegistration;
}
