package com.miguelsperle.nexbuy.module.user.application.dtos;

import com.miguelsperle.nexbuy.core.domain.abstractions.mediator.IRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePhysicalUserHandlerInput implements IRequest<Void> {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String cpf;
    private String generalRegister;
}
