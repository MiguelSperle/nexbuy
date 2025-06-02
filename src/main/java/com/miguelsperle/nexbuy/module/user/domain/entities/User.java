package com.miguelsperle.nexbuy.module.user.domain.entities;

import com.miguelsperle.nexbuy.module.user.domain.enums.AuthorizationRole;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import lombok.Data;
import lombok.With;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class User {
    private final String id;
    @With
    private final String firstName;
    @With
    private final String lastName;
    private final String email;
    @With
    private final String password;
    @With
    private final String phoneNumber;
    @With
    private final Boolean isVerified;
    private final AuthorizationRole authorizationRole;
    private final PersonType personType;
    private final LocalDateTime createdAt;

    private User(
            String id,
            String firstName,
            String lastName,
            String email,
            String password,
            String phoneNumber,
            Boolean isVerified,
            AuthorizationRole authorizationRole,
            PersonType personType,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.isVerified = isVerified;
        this.authorizationRole = authorizationRole;
        this.personType = personType;
        this.createdAt = createdAt;
    }

    public static User newUser(
            String firstName,
            String lastName,
            String email,
            String password,
            String phoneNumber,
            PersonType personType
    ) {
        return new User(
                UUID.randomUUID().toString(),
                firstName,
                lastName,
                email,
                password,
                phoneNumber,
                false,
                AuthorizationRole.CUSTOMER,
                personType,
                LocalDateTime.now()
        );
    }

    public static User with(
            String id,
            String firstName,
            String lastName,
            String email,
            String password,
            String phoneNumber,
            Boolean isVerified,
            AuthorizationRole authorizationRole,
            PersonType personType,
            LocalDateTime createdAt
    ) {
        return new User(
                id,
                firstName,
                lastName,
                email,
                password,
                phoneNumber,
                isVerified,
                authorizationRole,
                personType,
                createdAt
        );
    }
}
