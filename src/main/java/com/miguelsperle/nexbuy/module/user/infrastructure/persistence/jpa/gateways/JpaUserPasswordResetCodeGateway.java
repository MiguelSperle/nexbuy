package com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.gateways;

import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserPasswordResetCodeGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserPasswordResetCode;
import com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities.JpaUserPasswordResetCodeEntity;
import com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.repositories.JpaUserPasswordResetCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaUserPasswordResetCodeGateway implements IUserPasswordResetCodeGateway {
    private final JpaUserPasswordResetCodeRepository jpaUserPasswordResetCodeRepository;

    @Override
    public List<UserPasswordResetCode> findAll() {
        return this.jpaUserPasswordResetCodeRepository.findAll().stream().map(JpaUserPasswordResetCodeEntity::toEntity).toList();
    }

    @Override
    public Optional<UserPasswordResetCode> findById(String id) {
        return this.jpaUserPasswordResetCodeRepository.findById(id).map(JpaUserPasswordResetCodeEntity::toEntity);
    }

    @Override
    public UserPasswordResetCode save(UserPasswordResetCode userPasswordResetCode) {
        return this.jpaUserPasswordResetCodeRepository.save(JpaUserPasswordResetCodeEntity.from(userPasswordResetCode)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaUserPasswordResetCodeRepository.deleteById(id);
    }
}
