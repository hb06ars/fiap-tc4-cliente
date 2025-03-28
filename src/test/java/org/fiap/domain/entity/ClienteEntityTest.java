package org.fiap.domain.entity;

import org.fiap.domain.dto.ClienteDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ClienteEntityTest {

    private ClienteEntity clienteEntity;

    @BeforeEach
    void setUp() {
        ClienteDTO clienteDTO = ClienteDTO.builder()
                .id(1L)
                .nome("João Silva")
                .cpf("123.456.789-00")
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .rua("Rua Exemplo")
                .numero(123)
                .cep("12345-678")
                .cidade("Cidade Exemplo")
                .estado("Estado Exemplo")
                .complemento("Apt 101")
                .dtCriacao(LocalDate.now())
                .dtAtualizacao(LocalDate.now())
                .build();

        clienteEntity = new ClienteEntity(clienteDTO);
    }

    @Test
    void testNoArgsConstructor() {
        ClienteEntity clienteEntityEmpty = new ClienteEntity();
        assertNotNull(clienteEntityEmpty);
        assertNull(clienteEntityEmpty.getNome());
        assertNull(clienteEntityEmpty.getCpf());
    }

    @Test
    void testAllArgsConstructorFromDTO() {
        assertEquals(1L, clienteEntity.getId());
        assertEquals("João Silva", clienteEntity.getNome());
        assertEquals("123.456.789-00", clienteEntity.getCpf());
        assertEquals(LocalDate.of(1990, 1, 1), clienteEntity.getDataNascimento());
        assertEquals("Rua Exemplo", clienteEntity.getRua());
        assertEquals(123, clienteEntity.getNumero());
        assertEquals("12345-678", clienteEntity.getCep());
        assertEquals("Cidade Exemplo", clienteEntity.getCidade());
        assertEquals("Estado Exemplo", clienteEntity.getEstado());
        assertEquals("Apt 101", clienteEntity.getComplemento());
        assertNotNull(clienteEntity.getDtCriacao());
        assertNotNull(clienteEntity.getDtAtualizacao());
    }

    @Test
    void testSetter() {
        ClienteEntity clienteEntitySetter = new ClienteEntity();
        clienteEntitySetter.setNome("Maria Silva");
        clienteEntitySetter.setCpf("987.654.321-00");

        assertEquals("Maria Silva", clienteEntitySetter.getNome());
        assertEquals("987.654.321-00", clienteEntitySetter.getCpf());
    }

}
