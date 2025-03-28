package org.fiap.infra.repository.postgres;

import org.fiap.domain.entity.ClienteEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteRepositoryTest {

    @Mock
    private ClienteRepository clienteRepository;

    private ClienteEntity clienteEntity;

    @BeforeEach
    void setUp() {
        clienteEntity = new ClienteEntity();
        clienteEntity.setCpf("12345678901");
        clienteEntity.setNome("João Silva");
        clienteEntity.setCidade("São Paulo");
    }

    @Test
    void testFindByCpf() {
        when(clienteRepository.findByCpf(anyString())).thenReturn(clienteEntity);

        ClienteEntity foundCliente = clienteRepository.findByCpf("12345678901");

        assertNotNull(foundCliente);
        assertEquals("12345678901", foundCliente.getCpf());
        assertEquals("João Silva", foundCliente.getNome());
        assertEquals("São Paulo", foundCliente.getCidade());
    }

    @Test
    void testFindByCpfNotFound() {
        when(clienteRepository.findByCpf(anyString())).thenReturn(null);

        ClienteEntity foundCliente = clienteRepository.findByCpf("00000000000");

        assertNull(foundCliente);
    }
}