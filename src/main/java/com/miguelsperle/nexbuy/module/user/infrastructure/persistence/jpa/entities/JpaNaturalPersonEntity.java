package com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.user.domain.entities.NaturalPerson;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "physical_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JpaNaturalPersonEntity {
    @Id
    private String id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private JpaUserEntity jpaUserEntity;

    @Column(nullable = false, length = 14, unique = true)
    private String cpf;

    @Column(name = "general_register", length = 15, nullable = false, unique = true)
    private String generalRegister;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaNaturalPersonEntity from(NaturalPerson naturalPerson) {
        return new JpaNaturalPersonEntity(
                naturalPerson.getId(),
                JpaUserEntity.from(naturalPerson.getUser()),
                naturalPerson.getCpf(),
                naturalPerson.getGeneralRegister(),
                naturalPerson.getCreatedAt()
        );
    }

    public NaturalPerson toEntity() {
        return NaturalPerson.with(
                this.id,
                this.jpaUserEntity.toEntity(),
                this.cpf,
                this.generalRegister,
                this.createdAt
        );
    }
}
