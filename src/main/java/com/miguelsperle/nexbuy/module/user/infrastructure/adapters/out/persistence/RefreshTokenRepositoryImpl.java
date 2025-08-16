package com.miguelsperle.nexbuy.module.user.infrastructure.adapters.out.persistence;

import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.RefreshTokenRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.RefreshToken;
import com.miguelsperle.nexbuy.module.user.infrastructure.adapters.out.persistence.jpa.entities.JpaRefreshTokenEntity;
import com.miguelsperle.nexbuy.module.user.infrastructure.adapters.out.persistence.jpa.repositories.JpaRefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {
    private final JpaRefreshTokenRepository jpaRefreshTokenRepository;

    @Override
    public List<RefreshToken> findAll() {
        return this.jpaRefreshTokenRepository.findAll().stream().map(JpaRefreshTokenEntity::toEntity).toList();
    }

    @Override
    public Optional<RefreshToken> findById(String id) {
        return this.jpaRefreshTokenRepository.findById(id).map(JpaRefreshTokenEntity::toEntity);
    }

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return this.jpaRefreshTokenRepository.save(JpaRefreshTokenEntity.from(refreshToken)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaRefreshTokenRepository.deleteById(id);
    }

    @Override
    public Optional<RefreshToken> findByUserId(String userId) {
        return this.jpaRefreshTokenRepository.findByUserId(userId).map(JpaRefreshTokenEntity::toEntity);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return this.jpaRefreshTokenRepository.findByToken(token).map(JpaRefreshTokenEntity::toEntity);
    }
}
