package com.miguelsperle.nexbuy.module.user.domain.entities;

import com.miguelsperle.nexbuy.module.user.domain.enums.AuthorizationRole;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;

import java.time.LocalDateTime;
import java.util.UUID;

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

    public User withFirstName(String firstName) {
        return new User(
                this.id,
                firstName,
                this.lastName,
                this.email,
                this.password,
                this.phoneNumber,
                this.isVerified,
                this.authorizationRole,
                this.personType,
                this.createdAt
        );
    }

    public User withLastName(String lastName) {
        return new User(
                this.id,
                this.firstName,
                lastName,
                this.email,
                this.password,
                this.phoneNumber,
                this.isVerified,
                this.authorizationRole,
                this.personType,
                this.createdAt
        );
    }

    public User withPassword(String password) {
        return new User(
                this.id,
                this.firstName,
                this.lastName,
                this.email,
                password,
                this.phoneNumber,
                this.isVerified,
                this.authorizationRole,
                this.personType,
                this.createdAt
        );
    }

    public User withPhoneNumber(String phoneNumber) {
        return new User(
                this.id,
                this.firstName,
                this.lastName,
                this.email,
                this.password,
                phoneNumber,
                this.isVerified,
                this.authorizationRole,
                this.personType,
                this.createdAt
        );
    }

    public User withIsVerified(Boolean isVerified) {
        return new User(
                this.id,
                this.firstName,
                this.lastName,
                this.email,
                this.password,
                this.phoneNumber,
                isVerified,
                this.authorizationRole,
                this.personType,
                this.createdAt
        );
    }

    public String getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Boolean getIsVerified() {
        return this.isVerified;
    }

    public AuthorizationRole getAuthorizationRole() {
        return this.authorizationRole;
    }

    public PersonType getPersonType() {
        return this.personType;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + this.id + '\'' +
                ", firstName='" + this.firstName + '\'' +
                ", lastName='" + this.lastName + '\'' +
                ", email='" + this.email + '\'' +
                ", password='" + this.password + '\'' +
                ", phoneNumber='" + this.phoneNumber + '\'' +
                ", isVerified=" + this.isVerified +
                ", authorizationRole=" + this.authorizationRole +
                ", personType=" + this.personType +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
