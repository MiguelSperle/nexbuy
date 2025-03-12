package com.example.miguelsperle.nexbuy.module.user.domain.entities;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class JuridicalPerson {
    private final String id;
    private final User user;
    private final String cnpj;
    private final String fantasyName;
    private final String legalName;
    private final String stateRegistration;
    private final LocalDateTime createdAt;

    private JuridicalPerson(
            String id,
            User user,
            String cnpj,
            String fantasyName,
            String legalName,
            String stateRegistration,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.user = user;
        this.cnpj = cnpj;
        this.fantasyName = fantasyName;
        this.legalName = legalName;
        this.stateRegistration = stateRegistration;
        this.createdAt = createdAt;
    }

    public static JuridicalPerson newJuridicalPerson(
            User user,
            String cnpj,
            String fantasyName,
            String legalName,
            String stateRegistration
    ) {
        return new JuridicalPerson(
                UUID.randomUUID().toString(),
                user,
                cnpj,
                fantasyName,
                legalName,
                stateRegistration,
                LocalDateTime.now()
        );
    }

    public static JuridicalPerson with(
            String id,
            User user,
            String cnpj,
            String fantasyName,
            String legalName,
            String stateRegistration,
            LocalDateTime createdAt
    ) {
        return new JuridicalPerson(
                id,
                user,
                cnpj,
                fantasyName,
                legalName,
                stateRegistration,
                createdAt
        );
    }
}
