package com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.gateways;

import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IPhysicalUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.PhysicalUser;
import com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities.JpaPhysicalUserEntity;
import com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.repositories.JpaPhysicalUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaPhysicalUserGateway implements IPhysicalUserGateway {
    private final JpaPhysicalUserRepository jpaPhysicalUserRepository;

    @Override
    public List<PhysicalUser> findAll() {
        return this.jpaPhysicalUserRepository.findAll().stream().map(JpaPhysicalUserEntity::toEntity).toList();
    }

    @Override
    public Optional<PhysicalUser> findById(String id) {
        return this.jpaPhysicalUserRepository.findById(id).map(JpaPhysicalUserEntity::toEntity);
    }

    @Override
    public PhysicalUser save(PhysicalUser physicalUser) {
        return this.jpaPhysicalUserRepository.save(JpaPhysicalUserEntity.from(physicalUser)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaPhysicalUserRepository.deleteById(id);
    }
}
