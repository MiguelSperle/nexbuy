package com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.gateways;

import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserVerificationCodeGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserVerificationCode;
import com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities.JpaUserVerificationCodeEntity;
import com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.repositories.JpaUserVerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaUserVerificationCodeGateway implements IUserVerificationCodeGateway {
    private final JpaUserVerificationCodeRepository jpaUserVerificationCodeRepository;

    @Override
    public List<UserVerificationCode> findAll() {
        return this.jpaUserVerificationCodeRepository.findAll().stream().map(JpaUserVerificationCodeEntity::toEntity).toList();
    }

    @Override
    public Optional<UserVerificationCode> findById(String id) {
        return this.jpaUserVerificationCodeRepository.findById(id).map(JpaUserVerificationCodeEntity::toEntity);
    }

    @Override
    public UserVerificationCode save(UserVerificationCode userVerificationCode) {
        return this.jpaUserVerificationCodeRepository.save(JpaUserVerificationCodeEntity.from(userVerificationCode)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaUserVerificationCodeRepository.deleteById(id);
    }

    @Override
    public Optional<UserVerificationCode> findByUserId(String userId) {
        return this.jpaUserVerificationCodeRepository.findByUserId(userId).map(JpaUserVerificationCodeEntity::toEntity);
    }
}
