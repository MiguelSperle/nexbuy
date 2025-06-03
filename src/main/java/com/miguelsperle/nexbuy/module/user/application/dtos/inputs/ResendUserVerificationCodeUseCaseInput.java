package com.miguelsperle.nexbuy.module.user.application.dtos.inputs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResendUserVerificationCodeUseCaseInput {
    private String email;
}
