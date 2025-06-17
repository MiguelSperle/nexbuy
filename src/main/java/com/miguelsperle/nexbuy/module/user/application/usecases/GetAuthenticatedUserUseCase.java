package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.security.IAuthenticatedUserService;
import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.GetAuthenticatedUserUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.complements.PersonComplementOutput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.LegalPersonNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.NaturalPersonNotFoundException;
import com.miguelsperle.nexbuy.core.application.exceptions.AuthenticatedUserNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IGetAuthenticatedUserUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.ILegalPersonGateway;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.INaturalPersonGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.LegalPerson;
import com.miguelsperle.nexbuy.module.user.domain.entities.NaturalPerson;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAuthenticatedUserUseCase implements IGetAuthenticatedUserUseCase {
    private final IAuthenticatedUserService authenticatedUserService;
    private final INaturalPersonGateway naturalPersonGateway;
    private final ILegalPersonGateway legalPersonGateway;

    @Override
    public GetAuthenticatedUserUseCaseOutput execute() {
        final User authenticatedUser = this.getAuthenticatedUser();

        PersonComplementOutput personComplementOutput;

        if (authenticatedUser.getPersonType() == PersonType.NATURAL_PERSON) {
            final NaturalPerson naturalPerson = this.getNaturalPersonByUserId(authenticatedUser.getId());

            personComplementOutput = new PersonComplementOutput(
                    naturalPerson.getCpf(), naturalPerson.getGeneralRegister(),
                    null, null, null, null
            );
        } else {
            final LegalPerson legalPerson = this.getLegalPersonByUserId(authenticatedUser.getId());

            personComplementOutput = new PersonComplementOutput(
                    null, null, legalPerson.getCnpj(),
                    legalPerson.getFantasyName(), legalPerson.getLegalName(),
                    legalPerson.getStateRegistration()
            );
        }

        return new GetAuthenticatedUserUseCaseOutput(authenticatedUser, personComplementOutput);
    }

    private NaturalPerson getNaturalPersonByUserId(String userId) {
        return this.naturalPersonGateway.findByUserId(userId)
                .orElseThrow(() -> new NaturalPersonNotFoundException("Natural person not found"));
    }

    private LegalPerson getLegalPersonByUserId(String userId) {
        return this.legalPersonGateway.findByUserId(userId)
                .orElseThrow(() -> new LegalPersonNotFoundException("Legal person not found"));
    }

    private User getAuthenticatedUser() {
        return this.authenticatedUserService.getAuthenticatedUser()
                .orElseThrow(() -> new AuthenticatedUserNotFoundException("Authenticated user not found in security context"));
    }
}
