package com.example.miguelsperle.nexbuy.module.user.domain.entities;

import com.example.miguelsperle.nexbuy.module.user.domain.enums.AuthorizationRole;
import com.example.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class User {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final String phoneNumber;
    private final Boolean isVerified;
    private final AuthorizationRole authorizationRole;
    private final PersonType personType;
    private final String profilePhoto;
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
            String profilePhoto,
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
        this.profilePhoto = profilePhoto;
        this.createdAt = createdAt;
    }

    public static User newUser(
            String firstName,
            String lastName,
            String email,
            String password,
            String phoneNumber,
            AuthorizationRole authorizationRole,
            PersonType personType
    ) {
        final String PROFILE_PHOTO = "profile_photo/vv7hqagjqjzgitjazo3q";

        return new User(
                UUID.randomUUID().toString(),
                firstName,
                lastName,
                email,
                password,
                phoneNumber,
                false,
                authorizationRole,
                personType,
                PROFILE_PHOTO,
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
            String profilePhoto,
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
                profilePhoto,
                createdAt
        );
    }
}
