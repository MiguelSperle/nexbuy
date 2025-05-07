package com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.user.domain.entities.LegalPerson;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "juridical_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JpaLegalPersonEntity {
    @Id
    private String id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private JpaUserEntity jpaUserEntity;

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
                JpaUserEntity.from(legalPerson.getUser()),
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
                this.jpaUserEntity.toEntity(),
                this.cnpj,
                this.fantasyName,
                this.legalName,
                this.stateRegistration,
                this.createdAt
        );
    }
}
