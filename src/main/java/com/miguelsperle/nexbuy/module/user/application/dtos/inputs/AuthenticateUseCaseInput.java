package com.miguelsperle.nexbuy.module.user.application.dtos.inputs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticateUseCaseInput {
    private String email;
    private String password;
}
