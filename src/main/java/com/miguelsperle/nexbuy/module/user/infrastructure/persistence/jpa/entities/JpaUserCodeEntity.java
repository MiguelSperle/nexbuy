package com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.CodeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_codes")
@AllArgsConstructor
@NoArgsConstructor
public class JpaUserCodeEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;

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
                userCode.getUserId(),
                userCode.getCode(),
                userCode.getCodeType(),
                userCode.getExpiresIn(),
                userCode.getCreatedAt()
        );
    }

    public UserCode toEntity() {
        return UserCode.with(
                this.id,
                this.userId,
                this.code,
                this.codeType,
                this.expiresIn,
                this.createdAt
        );
    }
}
