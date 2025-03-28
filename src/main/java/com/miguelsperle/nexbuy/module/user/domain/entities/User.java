package com.miguelsperle.nexbuy.module.user.domain.entities;

import com.miguelsperle.nexbuy.module.user.domain.enums.AuthorizationRole;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class User {
    private final String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private Boolean isVerified;
    private AuthorizationRole authorizationRole;
    private UserType userType;
    private String profilePhoto;
    private LocalDateTime createdAt;

    private User(
            String id,
            String firstName,
            String lastName,
            String email,
            String password,
            String phoneNumber,
            Boolean isVerified,
            AuthorizationRole authorizationRole,
            UserType userType,
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
        this.userType = userType;
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
            UserType userType
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
                userType,
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
            UserType userType,
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
                userType,
                profilePhoto,
                createdAt
        );
    }
}
