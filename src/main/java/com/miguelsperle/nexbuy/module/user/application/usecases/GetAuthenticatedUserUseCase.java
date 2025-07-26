package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.application.ports.out.security.ISecurityContextService;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.GetAuthenticatedUserUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.complements.PersonComplementOutput;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.LegalPersonNotFoundException;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.NaturalPersonNotFoundException;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.UserNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.ports.in.IGetAuthenticatedUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.ILegalPersonRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.INaturalPersonRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.IUserRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.LegalPerson;
import com.miguelsperle.nexbuy.module.user.domain.entities.NaturalPerson;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;

public class GetAuthenticatedUserUseCase implements IGetAuthenticatedUserUseCase {
    private final ISecurityContextService securityContextService;
    private final INaturalPersonRepository naturalPersonRepository;
    private final ILegalPersonRepository legalPersonRepository;
    private final IUserRepository userRepository;

    public GetAuthenticatedUserUseCase(
            ISecurityContextService securityContextService,
            INaturalPersonRepository naturalPersonRepository,
            ILegalPersonRepository legalPersonRepository,
            IUserRepository userRepository
    ) {
        this.securityContextService = securityContextService;
        this.naturalPersonRepository = naturalPersonRepository;
        this.legalPersonRepository = legalPersonRepository;
        this.userRepository = userRepository;
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
        return this.naturalPersonRepository.findByUserId(userId)
                .orElseThrow(() -> NaturalPersonNotFoundException.with("Natural person not found"));
    }

    private LegalPerson getLegalPersonByUserId(String userId) {
        return this.legalPersonRepository.findByUserId(userId)
                .orElseThrow(() -> LegalPersonNotFoundException.with("Legal person not found"));
    }

    private String getAuthenticatedUserId() {
        return this.securityContextService.getAuthenticatedUserId();
    }

    private User getUserById(String userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.with("User not found"));
    }
}
