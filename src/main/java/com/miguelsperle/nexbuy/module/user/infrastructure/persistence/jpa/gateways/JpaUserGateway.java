package com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.gateways;

import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities.JpaUserEntity;
import com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.repositories.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaUserGateway implements IUserGateway {
    private final JpaUserRepository jpaUserRepository;

    @Override
    public List<User> findAll() {
        return this.jpaUserRepository.findAll().stream().map(JpaUserEntity::toEntity).toList();
    }

    @Override
    public Optional<User> findById(String id) {
        return this.jpaUserRepository.findById(id).map(JpaUserEntity::toEntity);
    }

    @Override
    public User save(User user) {
        return this.jpaUserRepository.save(JpaUserEntity.from(user)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaUserRepository.deleteById(id);
    }
}
