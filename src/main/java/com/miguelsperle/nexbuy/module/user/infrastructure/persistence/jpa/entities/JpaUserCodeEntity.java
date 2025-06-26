package com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.CodeType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_codes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JpaUserCodeEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private JpaUserEntity jpaUserEntity;

    @Column(nullable = false, unique = true, length = 6)
    private String code;

    @Column(name = "code_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private CodeType codeType;

    @Column(name = "expires_in", nullable = false)
    private LocalDateTime expiresIn;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaUserCodeEntity from(UserCode userCode) {
        return new JpaUserCodeEntity(
                userCode.getId(),
                JpaUserEntity.from(userCode.getUser()),
                userCode.getCode(),
                userCode.getCodeType(),
                userCode.getExpiresIn(),
                userCode.getCreatedAt()
        );
    }

    public UserCode toEntity() {
        return UserCode.with(
                this.id,
                this.jpaUserEntity.toEntity(),
                this.code,
                this.codeType,
                this.expiresIn,
                this.createdAt
        );
    }
}
