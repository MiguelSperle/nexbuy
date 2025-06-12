package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IPasswordEncryptorProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.transaction.ITransactionExecutor;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.CreateLegalPersonUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.CreateNaturalPersonUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.CreateUserUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.CreateUserVerificationCodeUseCaseInput;
import com.miguelsperle.nexbuy.core.application.exceptions.MissingRequiredComplementException;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.complements.PersonComplementInput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserAlreadyExistsException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateLegalPersonUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateNaturalPersonUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateUserVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateUserUseCase implements ICreateUserUseCase {
    private final IUserGateway userGateway;
    private final IPasswordEncryptorProvider passwordEncryptorProvider;
    private final ICreateLegalPersonUseCase createLegalPersonUseCase;
    private final ICreateNaturalPersonUseCase createNaturalPersonUseCase;
    private final ICreateUserVerificationCodeUseCase createUserVerificationCodeUseCase;
    private final ITransactionExecutor transactionExecutor;

    @Override
    public void execute(CreateUserUseCaseInput createUserUseCaseInput) {
        this.ensureComplementBasedPersonType(createUserUseCaseInput.getPersonType(),createUserUseCaseInput.getPersonComplementInput());

        if (this.verifyUserAlreadyExistsByEmail(createUserUseCaseInput.getEmail())) {
            throw new UserAlreadyExistsException("This email is already being used");
        }

        final String encodedPassword = this.passwordEncryptorProvider.encode(createUserUseCaseInput.getPassword());

        final User newUser = User.newUser(createUserUseCaseInput.getFirstName(), createUserUseCaseInput.getLastName(), createUserUseCaseInput.getEmail().toLowerCase(), encodedPassword, createUserUseCaseInput.getPhoneNumber(), createUserUseCaseInput.getPersonType());

        this.transactionExecutor.runTransaction(() -> {
            final User savedUser = this.userGateway.save(newUser);

            if (savedUser.getPersonType() == PersonType.NATURAL_PERSON) {
                this.createNaturalPersonUseCase.execute(new CreateNaturalPersonUseCaseInput(
                        savedUser,
                        createUserUseCaseInput.getPersonComplementInput().getCpf(),
                        createUserUseCaseInput.getPersonComplementInput().getGeneralRegister()
                ));
            } else {
                this.createLegalPersonUseCase.execute(new CreateLegalPersonUseCaseInput(
                        savedUser,
                        createUserUseCaseInput.getPersonComplementInput().getCnpj(),
                        createUserUseCaseInput.getPersonComplementInput().getFantasyName(),
                        createUserUseCaseInput.getPersonComplementInput().getLegalName(),
                        createUserUseCaseInput.getPersonComplementInput().getStateRegistration()
                ));
            }

            this.createUserVerificationCodeUseCase.execute(new CreateUserVerificationCodeUseCaseInput(
                    savedUser
            ));
        });
    }

    private boolean verifyUserAlreadyExistsByEmail(String email) {
        return this.userGateway.findByEmail(email).isPresent();
    }

    private void ensureComplementBasedPersonType(PersonType personType, PersonComplementInput personComplementInput) {
        if (personType == PersonType.NATURAL_PERSON && personComplementInput == null) {
            throw new MissingRequiredComplementException("You should provide naturalPersonComplement when the person type is a NATURAL_PERSON");
        } else if (personType == PersonType.LEGAL_PERSON && personComplementInput == null) {
            throw new MissingRequiredComplementException("You should provide legalPersonComplement when the person type is a LEGAL_PERSON");
        }
    }
}
