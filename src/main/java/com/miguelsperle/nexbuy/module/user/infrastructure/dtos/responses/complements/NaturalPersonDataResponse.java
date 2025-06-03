package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses.complements;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NaturalPersonDataResponse {
    private String cpf;
    private String generalRegister;
}
