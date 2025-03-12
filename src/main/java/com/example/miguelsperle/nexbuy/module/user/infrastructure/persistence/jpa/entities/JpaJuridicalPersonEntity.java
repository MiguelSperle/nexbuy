package com.example.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities;

import com.example.miguelsperle.nexbuy.module.user.domain.entities.JuridicalPerson;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "juridical_persons")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JpaJuridicalPersonEntity {
    @Id
    @Setter(AccessLevel.NONE)
    private String id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private JpaUserEntity jpaUserEntity;

    @Column(nullable = false, length = 18, unique = true)
    private String cnpj;

    @Column(name = "fantasy_name", nullable = false, length = 100)
    private String fantasyName;

    @Column(name = "legal_name", nullable = false, length = 100, unique = true)
    private String legalName;

    @Column(name = "state_registration", length = 15, unique = true)
    private String stateRegistration;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaJuridicalPersonEntity from(JuridicalPerson juridicalPerson) {
        return new JpaJuridicalPersonEntity(
                juridicalPerson.getId(),
                JpaUserEntity.from(juridicalPerson.getUser()),
                juridicalPerson.getCnpj(),
                juridicalPerson.getFantasyName(),
                juridicalPerson.getLegalName(),
                juridicalPerson.getStateRegistration(),
                juridicalPerson.getCreatedAt()
        );
    }

    public JuridicalPerson toEntity() {
        return JuridicalPerson.with(
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
