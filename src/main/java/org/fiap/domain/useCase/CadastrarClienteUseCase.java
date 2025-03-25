package org.fiap.domain.useCase;

import org.fiap.domain.dto.ClienteDTO;

public interface CadastrarClienteUseCase {
    ClienteDTO execute(ClienteDTO dto);
}
