package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses.complements;

import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.complements.PersonComplementOutput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NaturalPersonComplementResponse {
    private String cpf;
    private String generalRegister;

    public static NaturalPersonComplementResponse fromOutput(PersonComplementOutput personComplementOutput) {
        return new NaturalPersonComplementResponse(personComplementOutput.getCpf(), personComplementOutput.getGeneralRegister());
    }
}
