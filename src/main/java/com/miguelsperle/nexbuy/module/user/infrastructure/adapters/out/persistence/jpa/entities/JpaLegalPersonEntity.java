package com.miguelsperle.nexbuy.module.user.infrastructure.adapters.out.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.user.domain.entities.LegalPerson;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "legal_persons")
@AllArgsConstructor
@NoArgsConstructor
public class JpaLegalPersonEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(name = "user_id", nullable = false, unique = true, length = 36)
    private String userId;

    @Column(nullable = false, length = 18, unique = true)
    private String cnpj;

    @Column(name = "fantasy_name", nullable = false, length = 50)
    private String fantasyName;

    @Column(name = "legal_name", nullable = false, length = 50, unique = true)
    private String legalName;

    @Column(name = "state_registration", length = 25, unique = true)
    private String stateRegistration;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaLegalPersonEntity from(LegalPerson legalPerson) {
        return new JpaLegalPersonEntity(
                legalPerson.getId(),
                legalPerson.getUserId(),
                legalPerson.getCnpj(),
                legalPerson.getFantasyName(),
                legalPerson.getLegalName(),
                legalPerson.getStateRegistration(),
                legalPerson.getCreatedAt()
        );
    }

    public LegalPerson toEntity() {
        return LegalPerson.with(
                this.id,
                this.userId,
                this.cnpj,
                this.fantasyName,
                this.legalName,
                this.stateRegistration,
                this.createdAt
        );
    }
}
