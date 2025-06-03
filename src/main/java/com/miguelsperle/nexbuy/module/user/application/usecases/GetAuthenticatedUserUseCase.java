package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.security.IAuthenticatedUserService;
import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.GetAuthenticatedUserUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.complements.LegalPersonDataOutput;
import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.complements.NaturalPersonDataOutput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.LegalPersonNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.NaturalPersonNotFoundException;
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
        final User authenticatedUser = this.authenticatedUserService.getAuthenticatedUser();

        NaturalPersonDataOutput naturalPersonDataOutput = null;
        LegalPersonDataOutput legalPersonDataOutput = null;

        if (authenticatedUser.getPersonType() == PersonType.NATURAL_PERSON) {
            final NaturalPerson naturalPerson = this.getNaturalPersonByUserId(authenticatedUser.getId());

            naturalPersonDataOutput = new NaturalPersonDataOutput(naturalPerson.getCpf(), naturalPerson.getGeneralRegister());
        } else {
            final LegalPerson legalPerson = this.getLegalPersonByUserId(authenticatedUser.getId());

            legalPersonDataOutput = new LegalPersonDataOutput(legalPerson.getCnpj(), legalPerson.getFantasyName(),
                    legalPerson.getLegalName(), legalPerson.getStateRegistration()
            );
        }

        return new GetAuthenticatedUserUseCaseOutput(
                authenticatedUser.getFirstName(),
                authenticatedUser.getLastName(),
                authenticatedUser.getEmail(),
                authenticatedUser.getPhoneNumber(),
                authenticatedUser.getAuthorizationRole(),
                authenticatedUser.getPersonType(),
                naturalPersonDataOutput,
                legalPersonDataOutput
        );
    }

    private NaturalPerson getNaturalPersonByUserId(String userId) {
        return this.naturalPersonGateway.findByUserId(userId)
                .orElseThrow(() -> new NaturalPersonNotFoundException("Natural person not found"));
    }

    private LegalPerson getLegalPersonByUserId(String userId) {
        return this.legalPersonGateway.findByUserId(userId)
                .orElseThrow(() -> new LegalPersonNotFoundException("Legal person not found"));
    }
}
