package com.miguelsperle.nexbuy.module.user.application.handlers;

import com.miguelsperle.nexbuy.core.domain.abstractions.mediator.IRequestHandler;
import com.miguelsperle.nexbuy.module.user.application.dtos.*;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateJuridicalUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreatePhysicalUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateUserVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserType;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class CreateUserHandler implements IRequestHandler<CreateUserHandlerInput, Void> {
    private final ICreateUserUseCase createUserUseCase;
    private final ICreatePhysicalUserUseCase createPhysicalUserUseCase;
    private final ICreateJuridicalUserUseCase createJuridicalUserUseCase;
    private final ICreateUserVerificationCodeUseCase createUserVerificationCodeUseCase;

    @Override
    public Void handle(CreateUserHandlerInput createUserHandlerInput) {
        final CreateUserUseCaseOutput createUserUseCaseOutput = this.createUserUseCase.execute(new CreateUserUseCaseInput(
                createUserHandlerInput.getFirstName(),
                createUserHandlerInput.getLastName(),
                createUserHandlerInput.getEmail(),
                createUserHandlerInput.getPassword(),
                createUserHandlerInput.getPhoneNumber(),
                createUserHandlerInput.getUserType(),
                createUserHandlerInput.getCpf(),
                createUserHandlerInput.getGeneralRegister(),
                createUserHandlerInput.getCnpj(),
                createUserHandlerInput.getFantasyName(),
                createUserHandlerInput.getLegalName(),
                createUserHandlerInput.getStateRegistration()
        ));

        if (Objects.equals(createUserHandlerInput.getUserType(), UserType.PHYSICAL_USER.name())) {
            this.createPhysicalUserUseCase.execute(new CreatePhysicalUserUseCaseInput(
                    createUserUseCaseOutput.getUser(),
                    createUserHandlerInput.getCpf(),
                    createUserHandlerInput.getGeneralRegister()
            ));
        } else {
            this.createJuridicalUserUseCase.execute(new CreateJuridicalUserUseCaseInput(
                    createUserUseCaseOutput.getUser(),
                    createUserHandlerInput.getCnpj(),
                    createUserHandlerInput.getFantasyName(),
                    createUserHandlerInput.getLegalName(),
                    createUserHandlerInput.getStateRegistration()
            ));
        }

        this.createUserVerificationCodeUseCase.execute(new CreateUserVerificationCodeUseCaseInput(
                createUserUseCaseOutput.getUser()
        ));

        return null;
    }
}