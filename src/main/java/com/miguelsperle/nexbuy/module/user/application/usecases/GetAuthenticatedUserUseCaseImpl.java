package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.shared.application.ports.out.security.SecurityContextService;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.GetAuthenticatedUserUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.complements.PersonComplementOutput;
import com.miguelsperle.nexbuy.module.user.application.ports.in.GetAuthenticatedUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.LegalPersonRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.NaturalPersonRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.LegalPerson;
import com.miguelsperle.nexbuy.module.user.domain.entities.NaturalPerson;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

public class GetAuthenticatedUserUseCaseImpl implements GetAuthenticatedUserUseCase {
    private final SecurityContextService securityContextService;
    private final NaturalPersonRepository naturalPersonRepository;
    private final LegalPersonRepository legalPersonRepository;
    private final UserRepository userRepository;

    public GetAuthenticatedUserUseCaseImpl(
            SecurityContextService securityContextService,
            NaturalPersonRepository naturalPersonRepository,
            LegalPersonRepository legalPersonRepository,
            UserRepository userRepository
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
                .orElseThrow(() -> NotFoundException.with("Natural person not found"));
    }

    private LegalPerson getLegalPersonByUserId(String userId) {
        return this.legalPersonRepository.findByUserId(userId)
                .orElseThrow(() -> NotFoundException.with("Legal person not found"));
    }

    private String getAuthenticatedUserId() {
        return this.securityContextService.getAuthenticatedUserId();
    }

    private User getUserById(String userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> NotFoundException.with("User not found"));
    }
}
