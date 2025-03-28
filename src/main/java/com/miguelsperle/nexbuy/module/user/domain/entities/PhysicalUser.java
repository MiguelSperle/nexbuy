package com.miguelsperle.nexbuy.module.user.domain.entities;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PhysicalUser {
    private final String id;
    private User user;
    private String cpf;
    private String generalRegister;
    private LocalDateTime createdAt;

    private PhysicalUser(
            String id,
            User user,
            String cpf,
            String generalRegister,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.user = user;
        this.cpf = cpf;
        this.generalRegister = generalRegister;
        this.createdAt = createdAt;
    }

    public static PhysicalUser newPhysicalUser(
            User user,
            String cpf,
            String generalRegister
    ) {
        return new PhysicalUser(
                UUID.randomUUID().toString(),
                user,
                cpf,
                generalRegister,
                LocalDateTime.now()
        );
    }

    public static PhysicalUser with(
            String id,
            User user,
            String cpf,
            String generalRegister,
            LocalDateTime createdAt
    ) {
        return new PhysicalUser(
                id,
                user,
                cpf,
                generalRegister,
                createdAt
        );
    }
}
