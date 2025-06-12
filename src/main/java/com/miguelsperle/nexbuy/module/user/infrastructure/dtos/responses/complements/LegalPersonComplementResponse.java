package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses.complements;

import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.complements.PersonComplementOutput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LegalPersonComplementResponse {
    private String cnpj;
    private String fantasyName;
    private String legalName;
    private String stateRegistration;

    public static LegalPersonComplementResponse fromOutput(PersonComplementOutput personComplementOutput) {
        return new LegalPersonComplementResponse(personComplementOutput.getCnpj(), personComplementOutput.getFantasyName(),
                personComplementOutput.getLegalName(), personComplementOutput.getStateRegistration()
        );
    }
}
