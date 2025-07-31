package com.miguelsperle.nexbuy.module.user.infrastructure.adapters.out.persistence.jpa;

import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.AddressRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.Address;
import com.miguelsperle.nexbuy.module.user.infrastructure.adapters.out.persistence.jpa.entities.JpaAddressEntity;
import com.miguelsperle.nexbuy.module.user.infrastructure.adapters.out.persistence.jpa.repositories.JpaAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AddressRepositoryImpl implements AddressRepository {
    private final JpaAddressRepository jpaAddressRepository;

    @Override
    public List<Address> findAll() {
        return this.jpaAddressRepository.findAll().stream().map(JpaAddressEntity::toEntity).toList();
    }

    @Override
    public Optional<Address> findById(String id) {
        return this.jpaAddressRepository.findById(id).map(JpaAddressEntity::toEntity);
    }

    @Override
    public Address save(Address address) {
        return this.jpaAddressRepository.save(JpaAddressEntity.from(address)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaAddressRepository.deleteById(id);
    }

    @Override
    public List<Address> findAllByUserId(String userId) {
        return this.jpaAddressRepository.findAllByUserId(userId).stream().map(JpaAddressEntity::toEntity).toList();
    }
}
