package org.fiap.domain.dto;

import org.fiap.app.rest.request.ClienteRequest;
import org.fiap.domain.entity.ClienteEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ClienteDTOTest {

    private ClienteDTO clienteDTO;

    @BeforeEach
    void setUp() {
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setId(1L);
        clienteEntity.setNome("João Silva");
        clienteEntity.setCpf("123.456.789-00");
        clienteEntity.setDataNascimento(LocalDate.of(1990, 1, 1));
        clienteEntity.setRua("Rua Exemplo");
        clienteEntity.setNumero(123);
        clienteEntity.setCep("12345-678");
        clienteEntity.setCidade("Cidade Exemplo");
        clienteEntity.setEstado("Estado Exemplo");
        clienteEntity.setComplemento("Apt 101");
        clienteEntity.setDtCriacao(LocalDate.now());
        clienteEntity.setDtAtualizacao(LocalDate.now());

        clienteDTO = new ClienteDTO(clienteEntity);

    }

    @Test
    void testNoArgsConstructor() {
        ClienteDTO clienteDTOEmpty = new ClienteDTO();
        assertNotNull(clienteDTOEmpty);
        assertNull(clienteDTOEmpty.getNome());
        assertNull(clienteDTOEmpty.getCpf());
    }

    @Test
    void testCLientRequest() {
        ClienteRequest clienteRequest = new ClienteRequest();
        clienteRequest.setId(1L);
        clienteRequest.setNome("João Silva");
        clienteRequest.setCpf("123.456.789-00");
        clienteRequest.setDataNascimento(LocalDate.of(1990, 1, 1));
        clienteRequest.setRua("Rua Exemplo");
        clienteRequest.setNumero(123);
        clienteRequest.setCep("12345-678");
        clienteRequest.setCidade("Cidade Exemplo");
        clienteRequest.setEstado("Estado Exemplo");
        clienteRequest.setComplemento("Apt 101");
        clienteRequest.setDtCriacao(LocalDate.now());
        clienteRequest.setDtAtualizacao(LocalDate.now());
        ClienteDTO clienteDTO1 = new ClienteDTO(clienteRequest);
        assertNotNull(clienteDTO1);
    }

    @Test
    void testAllArgsConstructor() {
        ClienteDTO clienteDTOAllArgs = new ClienteDTO(1L, "João Silva", "123.456.789-00", LocalDate.of(1990, 1, 1),
                "Rua Exemplo", 123, "12345-678", "Cidade Exemplo", "Estado Exemplo", "Apt 101", LocalDate.now(), LocalDate.now());

        assertEquals(1L, clienteDTOAllArgs.getId());
        assertEquals("João Silva", clienteDTOAllArgs.getNome());
        assertEquals("123.456.789-00", clienteDTOAllArgs.getCpf());
    }

    @Test
    void testSetter() {
        ClienteDTO clienteDTOSetter = new ClienteDTO();
        clienteDTOSetter.setNome("Maria Silva");
        clienteDTOSetter.setCpf("987.654.321-00");

        assertEquals("Maria Silva", clienteDTOSetter.getNome());
        assertEquals("987.654.321-00", clienteDTOSetter.getCpf());
    }

    @Test
    void testBuilder() {
        ClienteDTO clienteDTOBuilder = ClienteDTO.builder()
                .id(2L)
                .nome("Carlos Souza")
                .cpf("111.222.333-44")
                .dataNascimento(LocalDate.of(1985, 5, 20))
                .rua("Avenida Brasil")
                .numero(456)
                .cep("54321-987")
                .cidade("São Paulo")
                .estado("SP")
                .complemento("Casa 1")
                .dtCriacao(LocalDate.now())
                .dtAtualizacao(LocalDate.now())
                .build();

        assertEquals(2L, clienteDTOBuilder.getId());
        assertEquals("Carlos Souza", clienteDTOBuilder.getNome());
        assertEquals("111.222.333-44", clienteDTOBuilder.getCpf());
    }

    @Test
    void testEqualsAndHashCode() {
        ClienteDTO cliente1 = new ClienteDTO(1L, "João Silva", "123.456.789-00", LocalDate.of(1990, 1, 1),
                "Rua Exemplo", 123, "12345-678", "Cidade Exemplo", "Estado Exemplo", "Apt 101", LocalDate.now(), LocalDate.now());
        ClienteDTO cliente2 = new ClienteDTO(1L, "João Silva", "123.456.789-00", LocalDate.of(1990, 1, 1),
                "Rua Exemplo", 123, "12345-678", "Cidade Exemplo", "Estado Exemplo", "Apt 101", LocalDate.now(), LocalDate.now());
        ClienteDTO cliente3 = new ClienteDTO(2L, "Carlos Souza", "111.222.333-44", LocalDate.of(1985, 5, 20),
                "Avenida Brasil", 456, "54321-987", "São Paulo", "SP", "Casa 1", LocalDate.now(), LocalDate.now());

        assertEquals(cliente1, cliente2);
        assertNotEquals(cliente1, cliente3);

        assertEquals(cliente1.hashCode(), cliente2.hashCode());
        assertNotEquals(cliente1.hashCode(), cliente3.hashCode());
    }
}
