package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.security.IAuthenticatedUserService;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.GetAuthenticatedUserUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.complements.PersonComplementOutput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.LegalPersonNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.NaturalPersonNotFoundException;
import com.miguelsperle.nexbuy.core.application.exceptions.AuthenticatedUserIdNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IGetAuthenticatedUserUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.ILegalPersonGateway;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.INaturalPersonGateway;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.LegalPerson;
import com.miguelsperle.nexbuy.module.user.domain.entities.NaturalPerson;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;

public class GetAuthenticatedUserUseCase implements IGetAuthenticatedUserUseCase {
    private final IAuthenticatedUserService authenticatedUserService;
    private final INaturalPersonGateway naturalPersonGateway;
    private final ILegalPersonGateway legalPersonGateway;
    private final IUserGateway userGateway;

    public GetAuthenticatedUserUseCase(
            IAuthenticatedUserService authenticatedUserService,
            INaturalPersonGateway naturalPersonGateway,
            ILegalPersonGateway legalPersonGateway,
            IUserGateway userGateway
    ) {
        this.authenticatedUserService = authenticatedUserService;
        this.naturalPersonGateway = naturalPersonGateway;
        this.legalPersonGateway = legalPersonGateway;
        this.userGateway = userGateway;
    }

    @Override
    public GetAuthenticatedUserUseCaseOutput execute() {
        final String authenticatedUserId = this.getAuthenticatedUserId();

        final User user = this.getUserById(authenticatedUserId);

        PersonComplementOutput personComplementOutput;

        if (user.getPersonType() == PersonType.NATURAL_PERSON) {
            final NaturalPerson naturalPerson = this.getNaturalPersonByUserId(user.getId());

            personComplementOutput = PersonComplementOutput.from(
                    naturalPerson.getCpf(), naturalPerson.getGeneralRegister(),
                    null, null, null, null
            );
        } else {
            final LegalPerson legalPerson = this.getLegalPersonByUserId(user.getId());

            personComplementOutput = PersonComplementOutput.from(
                    null, null, legalPerson.getCnpj(),
                    legalPerson.getFantasyName(), legalPerson.getLegalName(),
                    legalPerson.getStateRegistration()
            );
        }

        return GetAuthenticatedUserUseCaseOutput.from(user, personComplementOutput);
    }

    private NaturalPerson getNaturalPersonByUserId(String userId) {
        return this.naturalPersonGateway.findByUserId(userId)
                .orElseThrow(() -> new NaturalPersonNotFoundException("Natural person not found"));
    }

    private LegalPerson getLegalPersonByUserId(String userId) {
        return this.legalPersonGateway.findByUserId(userId)
                .orElseThrow(() -> new LegalPersonNotFoundException("Legal person not found"));
    }

    private String getAuthenticatedUserId() {
        return this.authenticatedUserService.getAuthenticatedUserId()
                .orElseThrow(() -> new AuthenticatedUserIdNotFoundException("Authenticated user id not found in security context"));
    }

    private User getUserById(String userId) {
        return this.userGateway.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
