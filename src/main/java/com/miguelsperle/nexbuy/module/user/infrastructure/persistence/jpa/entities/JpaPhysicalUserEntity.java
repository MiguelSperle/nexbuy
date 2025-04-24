package com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.user.domain.entities.PhysicalUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "physical_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JpaPhysicalUserEntity {
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

    public static JpaPhysicalUserEntity from(PhysicalUser physicalUser) {
        return new JpaPhysicalUserEntity(
                physicalUser.getId(),
                JpaUserEntity.from(physicalUser.getUser()),
                physicalUser.getCpf(),
                physicalUser.getGeneralRegister(),
                physicalUser.getCreatedAt()
        );
    }

    public PhysicalUser toEntity() {
        return PhysicalUser.with(
                this.id,
                this.jpaUserEntity.toEntity(),
                this.cpf,
                this.generalRegister,
                this.createdAt
        );
    }
}
