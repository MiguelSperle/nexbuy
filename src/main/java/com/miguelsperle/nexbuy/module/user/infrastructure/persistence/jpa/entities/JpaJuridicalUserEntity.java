package com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.user.domain.entities.JuridicalUser;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "juridical_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JpaJuridicalUserEntity {
    @Id
    @Setter(AccessLevel.NONE)
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

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaJuridicalUserEntity from(JuridicalUser juridicalUser) {
        return new JpaJuridicalUserEntity(
                juridicalUser.getId(),
                JpaUserEntity.from(juridicalUser.getUser()),
                juridicalUser.getCnpj(),
                juridicalUser.getFantasyName(),
                juridicalUser.getLegalName(),
                juridicalUser.getStateRegistration(),
                juridicalUser.getCreatedAt()
        );
    }

    public JuridicalUser toEntity() {
        return JuridicalUser.with(
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
