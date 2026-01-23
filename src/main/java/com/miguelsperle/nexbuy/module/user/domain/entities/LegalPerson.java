package com.miguelsperle.nexbuy.module.user.domain.entities;

import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

import java.time.LocalDateTime;

public class LegalPerson {
    private final String id;
    private final String userId;
    private final String cnpj;
    private final String fantasyName;
    private final String legalName;
    private final String stateRegistration;
    private final LocalDateTime createdAt;

    private LegalPerson(
            String id,
            String userId,
            String cnpj,
            String fantasyName,
            String legalName,
            String stateRegistration,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.cnpj = cnpj;
        this.fantasyName = fantasyName;
        this.legalName = legalName;
        this.stateRegistration = stateRegistration;
        this.createdAt = createdAt;
    }

    public static LegalPerson newLegalPerson(
            String userId,
            String cnpj,
            String fantasyName,
            String legalName,
            String stateRegistration
    ) {
        return new LegalPerson(
                IdentifierUtils.generateUUID(),
                userId,
                cnpj,
                fantasyName,
                legalName,
                stateRegistration,
                TimeUtils.now()
        );
    }

    public static LegalPerson with(
            String id,
            String userId,
            String cnpj,
            String fantasyName,
            String legalName,
            String stateRegistration,
            LocalDateTime createdAt
    ) {
        return new LegalPerson(
                id,
                userId,
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

    public String getUserId() {
        return this.userId;
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
                ", userId=" + this.userId +
                ", cnpj='" + this.cnpj + '\'' +
                ", fantasyName='" + this.fantasyName + '\'' +
                ", legalName='" + this.legalName + '\'' +
                ", stateRegistration='" + this.stateRegistration + '\'' +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
