package org.fiap.domain.useCase.impl;

import lombok.extern.slf4j.Slf4j;
import org.fiap.app.service.postgres.ClienteService;
import org.fiap.domain.dto.ClienteDTO;
import org.fiap.domain.useCase.CadastrarClienteUseCase;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CadastrarClienteUseCaseImpl implements CadastrarClienteUseCase {

    private final ClienteService service;

    public CadastrarClienteUseCaseImpl(ClienteService service) {
        this.service = service;
    }

    @Override
    public ClienteDTO execute(ClienteDTO dto) {
        return service.save(dto);
    }
}
