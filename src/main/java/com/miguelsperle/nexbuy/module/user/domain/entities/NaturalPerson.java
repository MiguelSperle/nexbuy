package com.miguelsperle.nexbuy.module.user.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class NaturalPerson {
    private final String id;
    private final User user;
    private final String cpf;
    private final String generalRegister;
    private final LocalDateTime createdAt;

    private NaturalPerson(
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

    public static NaturalPerson newNaturalPerson(
            User user,
            String cpf,
            String generalRegister
    ) {
        return new NaturalPerson(
                UUID.randomUUID().toString(),
                user,
                cpf,
                generalRegister,
                LocalDateTime.now()
        );
    }

    public static NaturalPerson with(
            String id,
            User user,
            String cpf,
            String generalRegister,
            LocalDateTime createdAt
    ) {
        return new NaturalPerson(
                id,
                user,
                cpf,
                generalRegister,
                createdAt
        );
    }

    public String getId() {
        return this.id;
    }

    public User getUser() {
        return this.user;
    }

    public String getCpf() {
        return this.cpf;
    }

    public String getGeneralRegister() {
        return this.generalRegister;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String toString() {
        return "NaturalPerson{" +
                "id='" + this.id + '\'' +
                ", user=" + this.user +
                ", cpf='" + this.cpf + '\'' +
                ", generalRegister='" + this.generalRegister + '\'' +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
