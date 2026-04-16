package com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities.JpaNaturalPersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaNaturalPersonRepository extends JpaRepository<JpaNaturalPersonEntity, String> {
    @Query(nativeQuery = true, value = "SELECT EXISTS (SELECT 1 FROM natural_persons np WHERE np.cpf = :cpf)")
    boolean existsByCpf(@Param("cpf") String cpf);

    @Query(nativeQuery = true, value = "SELECT EXISTS (SELECT 1 FROM natural_persons np WHERE np.general_register = :generalRegister)")
    boolean existsByGeneralRegister(@Param("generalRegister") String generalRegister);

    @Query(nativeQuery = true, value = "SELECT * FROM natural_persons np WHERE np.user_id = :userId")
    Optional<JpaNaturalPersonEntity> findByUserId(@Param("userId") String userId);
}
