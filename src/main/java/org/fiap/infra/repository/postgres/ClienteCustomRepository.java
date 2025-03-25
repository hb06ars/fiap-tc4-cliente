package org.fiap.infra.repository.postgres;

import org.fiap.domain.dto.ClienteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteCustomRepository {
    Page<ClienteDTO> findAllByCriteria(ClienteDTO dto, Pageable pageable, String sortField, String sortDirection);
}
