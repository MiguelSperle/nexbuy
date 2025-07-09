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
        return this.id;
    }

    public User getUser() {
        return this.user;
    }

    public String getCnpj() {
        return this.cnpj;
    }

    public String getFantasyName() {
        return this.fantasyName;
    }

    public String getLegalName() {
        return this.legalName;
    }

    public String getStateRegistration() {
        return this.stateRegistration;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String toString() {
        return "LegalPerson{" +
                "id='" + this.id + '\'' +
                ", user=" + this.user +
                ", cnpj='" + this.cnpj + '\'' +
                ", fantasyName='" + this.fantasyName + '\'' +
                ", legalName='" + this.legalName + '\'' +
                ", stateRegistration='" + this.stateRegistration + '\'' +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
