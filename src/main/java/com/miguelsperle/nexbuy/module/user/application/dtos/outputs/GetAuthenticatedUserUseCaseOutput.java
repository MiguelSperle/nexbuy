package com.miguelsperle.nexbuy.module.user.application.dtos.outputs;

import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.complements.PersonComplementOutput;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAuthenticatedUserUseCaseOutput {
    private User authenticatedUser;
    private PersonComplementOutput personComplementOutput;
}
