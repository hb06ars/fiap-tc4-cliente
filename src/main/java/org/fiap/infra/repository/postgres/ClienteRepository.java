package org.fiap.infra.repository.postgres;

import org.fiap.domain.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
    @Query("SELECT u FROM ClienteEntity u WHERE u.cpf like :cpf ")
    ClienteEntity findByCpf(@Param("cpf") String cpf);
}