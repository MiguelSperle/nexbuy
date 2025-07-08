package com.miguelsperle.nexbuy.module.user.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class LegalPerson {
    private final String id;
    private final User user;
    private final String cnpj;
    private final String fantasyName;
    private final String legalName;
    private final String stateRegistration;
    private final LocalDateTime createdAt;

    private LegalPerson(
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

    public static LegalPerson newLegalPerson(
            User user,
            String cnpj,
            String fantasyName,
            String legalName,
            String stateRegistration
    ) {
        return new LegalPerson(
                UUID.randomUUID().toString(),
                user,
                cnpj,
                fantasyName,
                legalName,
                stateRegistration,
                LocalDateTime.now()
        );
    }

    public static LegalPerson with(
            String id,
            User user,
            String cnpj,
            String fantasyName,
            String legalName,
            String stateRegistration,
            LocalDateTime createdAt
    ) {
        return new LegalPerson(
                id,
                user,
                cnpj,
                fantasyName,
                legalName,
                stateRegistration,
                createdAt
        );
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getFantasyName() {
        return fantasyName;
    }

    public String getLegalName() {
        return legalName;
    }

    public String getStateRegistration() {
        return stateRegistration;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "LegalPerson{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", cnpj='" + cnpj + '\'' +
                ", fantasyName='" + fantasyName + '\'' +
                ", legalName='" + legalName + '\'' +
                ", stateRegistration='" + stateRegistration + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
