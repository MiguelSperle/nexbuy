package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses.bases;

import com.miguelsperle.nexbuy.module.user.domain.enums.AuthorizationRole;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseAuthenticatedUserResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private AuthorizationRole authorizationRole;
    private PersonType personType;
}
