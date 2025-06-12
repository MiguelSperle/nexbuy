package com.miguelsperle.nexbuy.module.user.application.dtos.inputs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserPasswordUseCaseInput {
    private String currentPassword;
    private String password;
}
