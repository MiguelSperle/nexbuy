package com.miguelsperle.nexbuy.module.user.application.handlers;

import com.miguelsperle.nexbuy.core.domain.abstractions.mediator.IRequestHandler;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateJuridicalUserHandlerInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateJuridicalUserUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateUserUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateUserUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateJuridicalUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateUserUseCase;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateJuridicalUserHandler implements IRequestHandler<CreateJuridicalUserHandlerInput, Void> {
    private final ICreateUserUseCase createUserUseCase;
    private final ICreateJuridicalUserUseCase createJuridicalUserUseCase;

    @Override
    public Void handle(CreateJuridicalUserHandlerInput createJuridicalUserHandlerInput) {
        final CreateUserUseCaseOutput createUserUseCaseOutput = this.createUserUseCase.execute(new CreateUserUseCaseInput(createJuridicalUserHandlerInput.getFirstName(), createJuridicalUserHandlerInput.getLastName(),
                createJuridicalUserHandlerInput.getEmail(), createJuridicalUserHandlerInput.getPassword(), createJuridicalUserHandlerInput.getPhoneNumber(), UserType.JURIDICAL_USER,
                null, null, createJuridicalUserHandlerInput.getCnpj(), createJuridicalUserHandlerInput.getFantasyName(), createJuridicalUserHandlerInput.getLegalName(), createJuridicalUserHandlerInput.getStateRegistration()
        ));

        this.createJuridicalUserUseCase.execute(new CreateJuridicalUserUseCaseInput(createUserUseCaseOutput.getUser(),
                createJuridicalUserHandlerInput.getCnpj(), createJuridicalUserHandlerInput.getFantasyName(),
                createJuridicalUserHandlerInput.getLegalName(), createJuridicalUserHandlerInput.getStateRegistration()
        ));

        return null;
    }
}
