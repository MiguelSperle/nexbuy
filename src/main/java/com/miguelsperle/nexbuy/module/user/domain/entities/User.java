package com.miguelsperle.nexbuy.module.user.domain.entities;

import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;
import com.miguelsperle.nexbuy.module.user.domain.enums.AuthorizationRole;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserStatus;

import java.time.LocalDateTime;

public class User {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final String phoneNumber;
    private final UserStatus userStatus;
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
            UserStatus userStatus,
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
        this.userStatus = userStatus;
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
                IdentifierUtils.generateUUID(),
                firstName,
                lastName,
                email,
                password,
                phoneNumber,
                UserStatus.UNVERIFIED,
                AuthorizationRole.CUSTOMER,
                personType,
                TimeUtils.now()
        );
    }

    public static User with(
            String id,
            String firstName,
            String lastName,
            String email,
            String password,
            String phoneNumber,
            UserStatus userStatus,
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
                userStatus,
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
                this.userStatus,
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
                this.userStatus,
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
                this.userStatus,
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
                this.userStatus,
                this.authorizationRole,
                this.personType,
                this.createdAt
        );
    }

    public User withUserStatus(UserStatus userStatus) {
        return new User(
                this.id,
                this.firstName,
                this.lastName,
                this.email,
                this.password,
                this.phoneNumber,
                userStatus,
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

    public UserStatus getUserStatus() {
        return this.userStatus;
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
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", userStatus=" + userStatus +
                ", authorizationRole=" + authorizationRole +
                ", personType=" + personType +
                ", createdAt=" + createdAt +
                '}';
    }
}
