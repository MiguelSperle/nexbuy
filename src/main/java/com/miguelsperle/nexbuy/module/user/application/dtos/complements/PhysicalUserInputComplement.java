package com.miguelsperle.nexbuy.module.user.application.dtos.complements;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalUserInputComplement {
    private String cpf;
    private String generalRegister;
}
