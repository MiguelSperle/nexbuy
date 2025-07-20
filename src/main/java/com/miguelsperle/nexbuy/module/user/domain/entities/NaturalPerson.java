package com.miguelsperle.nexbuy.module.user.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class NaturalPerson {
    private final String id;
    private final String userId;
    private final String cpf;
    private final String generalRegister;
    private final LocalDateTime createdAt;

    private NaturalPerson(
            String id,
            String userId,
            String cpf,
            String generalRegister,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.cpf = cpf;
        this.generalRegister = generalRegister;
        this.createdAt = createdAt;
    }

    public static NaturalPerson newNaturalPerson(
            String userId,
            String cpf,
            String generalRegister
    ) {
        return new NaturalPerson(
                UUID.randomUUID().toString(),
                userId,
                cpf,
                generalRegister,
                LocalDateTime.now()
        );
    }

    public static NaturalPerson with(
            String id,
            String userId,
            String cpf,
            String generalRegister,
            LocalDateTime createdAt
    ) {
        return new NaturalPerson(
                id,
                userId,
                cpf,
                generalRegister,
                createdAt
        );
    }

    public String getId() {
        return this.id;
    }

    public String getUserId() {
        return this.userId;
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
                ", userId=" + this.userId +
                ", cpf='" + this.cpf + '\'' +
                ", generalRegister='" + this.generalRegister + '\'' +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
