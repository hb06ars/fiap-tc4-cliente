package org.fiap.domain.useCase.impl;

import org.fiap.app.service.postgres.ClienteService;
import org.fiap.domain.dto.ClienteDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CadastrarClienteUseCaseImplTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private CadastrarClienteUseCaseImpl cadastrarClienteUseCase;

    private ClienteDTO clienteDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        clienteDTO = ClienteDTO.builder()
                .id(1L)
                .nome("João Silva")
                .cpf("123.456.789-00")
                .dataNascimento(null)
                .rua("Rua Exemplo")
                .numero(123)
                .cep("12345-678")
                .cidade("Cidade Exemplo")
                .estado("Estado Exemplo")
                .complemento("Apt 101")
                .dtCriacao(null)
                .dtAtualizacao(null)
                .build();
    }

    @Test
    void testExecute() {
        when(clienteService.save(clienteDTO)).thenReturn(clienteDTO);
        ClienteDTO result = cadastrarClienteUseCase.execute(clienteDTO);
        assertEquals(clienteDTO, result);
    }

    @Test
    void testExecuteWithNullClient() {
        when(clienteService.save(null)).thenReturn(null);
        ClienteDTO result = cadastrarClienteUseCase.execute(null);
        assertEquals(null, result);
    }
}
