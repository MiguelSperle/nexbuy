package com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.user.domain.entities.NaturalPerson;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "natural_persons")
@AllArgsConstructor
@NoArgsConstructor
public class JpaNaturalPersonEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(name = "user_id", nullable = false, unique = true, length = 36)
    private String userId;

    @Column(nullable = false, length = 14, unique = true)
    private String cpf;

    @Column(name = "general_register", length = 15, nullable = false, unique = true)
    private String generalRegister;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaNaturalPersonEntity from(NaturalPerson naturalPerson) {
        return new JpaNaturalPersonEntity(
                naturalPerson.getId(),
                naturalPerson.getUserId(),
                naturalPerson.getCpf(),
                naturalPerson.getGeneralRegister(),
                naturalPerson.getCreatedAt()
        );
    }

    public NaturalPerson toEntity() {
        return NaturalPerson.with(
                this.id,
                this.userId,
                this.cpf,
                this.generalRegister,
                this.createdAt
        );
    }
}
