package com.miguelsperle.nexbuy.module.user.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePhysicalUserUseCaseInput {
    private String userId;
    private String cpf;
    private String generalRegister;
}
