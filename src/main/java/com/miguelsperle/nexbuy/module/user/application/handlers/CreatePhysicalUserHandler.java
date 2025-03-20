package com.miguelsperle.nexbuy.module.user.application.handlers;

import com.miguelsperle.nexbuy.core.domain.abstractions.mediator.IRequestHandler;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreatePhysicalUserHandlerInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreatePhysicalUserUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateUserUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateUserUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreatePhysicalUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateUserUseCase;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreatePhysicalUserHandler implements IRequestHandler<CreatePhysicalUserHandlerInput, Void> {
    private final ICreateUserUseCase createUserUseCase;
    private final ICreatePhysicalUserUseCase createPhysicalUserUseCase;

    @Override
    public Void handle(CreatePhysicalUserHandlerInput createPhysicalUserHandlerInput) {
        final CreateUserUseCaseOutput createUserUseCaseOutput = this.createUserUseCase.execute(new CreateUserUseCaseInput(createPhysicalUserHandlerInput.getFirstName(), createPhysicalUserHandlerInput.getLastName(),
                createPhysicalUserHandlerInput.getEmail(), createPhysicalUserHandlerInput.getPassword(), createPhysicalUserHandlerInput.getPhoneNumber(), UserType.PHYSICAL_USER,
                createPhysicalUserHandlerInput.getCpf(), createPhysicalUserHandlerInput.getGeneralRegister(), null, null, null
        ));

        this.createPhysicalUserUseCase.execute(new CreatePhysicalUserUseCaseInput(createUserUseCaseOutput.getUser(),
                createPhysicalUserHandlerInput.getCpf(), createPhysicalUserHandlerInput.getGeneralRegister()
        ));

        return null;
    }
}
