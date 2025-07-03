package com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.gateways;

import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.INaturalPersonGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.NaturalPerson;
import com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities.JpaNaturalPersonEntity;
import com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.repositories.JpaNaturalPersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaNaturalPersonGateway implements INaturalPersonGateway {
    private final JpaNaturalPersonRepository jpaNaturalPersonRepository;

    @Override
    public List<NaturalPerson> findAll() {
        return this.jpaNaturalPersonRepository.findAll().stream().map(JpaNaturalPersonEntity::toEntity).toList();
    }

    @Override
    public Optional<NaturalPerson> findById(String id) {
        return this.jpaNaturalPersonRepository.findById(id).map(JpaNaturalPersonEntity::toEntity);
    }

    @Override
    public NaturalPerson save(NaturalPerson naturalPerson) {
        return this.jpaNaturalPersonRepository.save(JpaNaturalPersonEntity.from(naturalPerson)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaNaturalPersonRepository.deleteById(id);
    }

    @Override
    public boolean existsByCpf(String cpf) {
        return this.jpaNaturalPersonRepository.existsByCpf(cpf);
    }

    @Override
    public boolean existsByGeneralRegister(String generalRegister) {
        return this.jpaNaturalPersonRepository.existsByGeneralRegister(generalRegister);
    }

    @Override
    public Optional<NaturalPerson> findByUserId(String userId) {
        return this.jpaNaturalPersonRepository.findByUserId(userId).map(JpaNaturalPersonEntity::toEntity);
    }
}
