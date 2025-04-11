package com.miguelsperle.nexbuy.module.user.domain.entities;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class JuridicalUser {
    private String id;
    private User user;
    private String cnpj;
    private String fantasyName;
    private String legalName;
    private String stateRegistration;
    private LocalDateTime createdAt;

    private JuridicalUser(
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

    public static JuridicalUser newJuridicalUser(
            User user,
            String cnpj,
            String fantasyName,
            String legalName,
            String stateRegistration
    ) {
        return new JuridicalUser(
                UUID.randomUUID().toString(),
                user,
                cnpj,
                fantasyName,
                legalName,
                stateRegistration,
                LocalDateTime.now()
        );
    }

    public static JuridicalUser with(
            String id,
            User user,
            String cnpj,
            String fantasyName,
            String legalName,
            String stateRegistration,
            LocalDateTime createdAt
    ) {
        return new JuridicalUser(
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
