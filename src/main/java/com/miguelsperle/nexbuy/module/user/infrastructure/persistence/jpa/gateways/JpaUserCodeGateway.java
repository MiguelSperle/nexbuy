package com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.gateways;

import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserCodeGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities.JpaUserCodeEntity;
import com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.repositories.JpaUserCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaUserCodeGateway implements IUserCodeGateway {
    private final JpaUserCodeRepository jpaUserCodeRepository;

    @Override
    public List<UserCode> findAll() {
        return this.jpaUserCodeRepository.findAll().stream().map(JpaUserCodeEntity::toEntity).toList();
    }

    @Override
    public Optional<UserCode> findById(String id) {
        return this.jpaUserCodeRepository.findById(id).map(JpaUserCodeEntity::toEntity);
    }

    @Override
    public UserCode save(UserCode userCode) {
        return this.jpaUserCodeRepository.save(JpaUserCodeEntity.from(userCode)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaUserCodeRepository.deleteById(id);
    }

    @Override
    public Optional<UserCode> findByUserIdAndCodeType(String userId, String codeType) {
        return this.jpaUserCodeRepository.findByUserIdAndCodeType(userId, codeType).map(JpaUserCodeEntity::toEntity);
    }

    @Override
    public Optional<UserCode> findByCodeAndCodeType(String code, String codeType) {
        return this.jpaUserCodeRepository.findByCodeAndCodeType(code, codeType).map(JpaUserCodeEntity::toEntity);
    }
}
