package com.example.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities;

import com.example.miguelsperle.nexbuy.module.user.domain.entities.PhysicalPerson;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "physical_persons")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JpaPhysicalPersonEntity {
    @Id
    @Setter(AccessLevel.NONE)
    private String id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private JpaUserEntity jpaUserEntity;

    @Column(nullable = false, length = 14, unique = true)
    private String cpf;

    @Column(name = "general_register", length = 15, nullable = false, unique = true)
    private String generalRegister;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaPhysicalPersonEntity from(PhysicalPerson physicalPerson) {
        return new JpaPhysicalPersonEntity(
                physicalPerson.getId(),
                JpaUserEntity.from(physicalPerson.getUser()),
                physicalPerson.getCpf(),
                physicalPerson.getGeneralRegister(),
                physicalPerson.getCreatedAt()
        );
    }

    public PhysicalPerson toEntity() {
        return PhysicalPerson.with(
                this.id,
                this.jpaUserEntity.toEntity(),
                this.cpf,
                this.generalRegister,
                this.createdAt
        );
    }
}
