package com.miguelsperle.nexbuy.module.user.domain.entities;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PhysicalPerson {
    private final String id;
    private final User user;
    private final String cpf;
    private final String generalRegister;
    private final LocalDateTime createdAt;

    private PhysicalPerson(
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

    public static PhysicalPerson newPhysicalPerson(
            User user,
            String cpf,
            String generalRegister
    ) {
        return new PhysicalPerson(
                UUID.randomUUID().toString(),
                user,
                cpf,
                generalRegister,
                LocalDateTime.now()
        );
    }

    public static PhysicalPerson with(
            String id,
            User user,
            String cpf,
            String generalRegister,
            LocalDateTime createdAt
    ) {
        return new PhysicalPerson(
                id,
                user,
                cpf,
                generalRegister,
                createdAt
        );
    }
}
