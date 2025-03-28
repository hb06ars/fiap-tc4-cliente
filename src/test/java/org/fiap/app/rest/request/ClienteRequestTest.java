package org.fiap.app.rest.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClienteRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidClienteRequest() {
        ClienteRequest clienteRequest = new ClienteRequest();
        clienteRequest.setNome("João da Silva");
        clienteRequest.setCpf("12345678901");
        clienteRequest.setDataNascimento(LocalDate.of(1990, 1, 1));
        clienteRequest.setRua("Rua Teste");
        clienteRequest.setNumero(123);
        clienteRequest.setCep("12345000");
        clienteRequest.setCidade("São Paulo");
        clienteRequest.setEstado("SP");
        clienteRequest.setComplemento("Apt 101");

        Set<ConstraintViolation<ClienteRequest>> violations = validator.validate(clienteRequest);
        assertTrue(violations.isEmpty());


        assertNotNull(clienteRequest.getNome());
        assertNotNull(clienteRequest.getCpf());
        assertNotNull(clienteRequest.getDataNascimento());
        assertNotNull(clienteRequest.getRua());
        assertNotNull(clienteRequest.getNumero());
        assertNotNull(clienteRequest.getCep());
        assertNotNull(clienteRequest.getCidade());
        assertNotNull(clienteRequest.getEstado());
        assertNotNull(clienteRequest.getComplemento());
    }

    @Test
    void testInvalidClienteRequestMissingNome() {
        ClienteRequest clienteRequest = ClienteRequest.builder()
                .cpf("12345678901")
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .rua("Rua Teste")
                .numero(123)
                .cep("12345000")
                .cidade("São Paulo")
                .estado("SP")
                .complemento("Apt 101")
                .build();

        Set<ConstraintViolation<ClienteRequest>> violations = validator.validate(clienteRequest);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nome")));
    }

    @Test
    void testInvalidClienteRequestInvalidCpf() {
        ClienteRequest clienteRequest = ClienteRequest.builder()
                .nome("João da Silva")
                .cpf("12345")
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .rua("Rua Teste")
                .numero(123)
                .cep("12345000")
                .cidade("São Paulo")
                .estado("SP")
                .complemento("Apt 101")
                .build();

        Set<ConstraintViolation<ClienteRequest>> violations = validator.validate(clienteRequest);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("cpf")));
    }

    @Test
    void testInvalidClienteRequestEmptyCpf() {
        ClienteRequest clienteRequest = ClienteRequest.builder()
                .nome("João da Silva")
                .cpf("")
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .rua("Rua Teste")
                .numero(123)
                .cep("12345000")
                .cidade("São Paulo")
                .estado("SP")
                .complemento("Apt 101")
                .build();

        Set<ConstraintViolation<ClienteRequest>> violations = validator.validate(clienteRequest);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("cpf")));
    }

    @Test
    void testInvalidClienteRequestNomeAndCpf() {
        ClienteRequest clienteRequest = ClienteRequest.builder()
                .nome(null)
                .cpf("12345")
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .rua("Rua Teste")
                .numero(123)
                .cep("12345000")
                .cidade("São Paulo")
                .estado("SP")
                .complemento("Apt 101")
                .build();

        Set<ConstraintViolation<ClienteRequest>> violations = validator.validate(clienteRequest);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nome")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("cpf")));
    }
}
