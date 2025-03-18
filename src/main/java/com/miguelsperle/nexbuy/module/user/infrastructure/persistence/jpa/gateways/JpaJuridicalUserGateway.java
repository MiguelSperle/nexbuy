package com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.gateways;

import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IJuridicalUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.JuridicalUser;
import com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities.JpaJuridicalUserEntity;
import com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.repositories.JpaJuridicalUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaJuridicalUserGateway implements IJuridicalUserGateway {
    private final JpaJuridicalUserRepository jpaJuridicalUserRepository;

    @Override
    public List<JuridicalUser> findAll() {
        return this.jpaJuridicalUserRepository.findAll().stream().map(JpaJuridicalUserEntity::toEntity).toList();
    }

    @Override
    public Optional<JuridicalUser> findById(String id) {
        return this.jpaJuridicalUserRepository.findById(id).map(JpaJuridicalUserEntity::toEntity);
    }

    @Override
    public JuridicalUser save(JuridicalUser juridicalUser) {
        return this.jpaJuridicalUserRepository.save(JpaJuridicalUserEntity.from(juridicalUser)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaJuridicalUserRepository.deleteById(id);
    }
}
