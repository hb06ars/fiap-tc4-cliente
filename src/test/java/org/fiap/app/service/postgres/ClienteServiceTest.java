package org.fiap.app.service.postgres;

import org.fiap.domain.dto.ClienteDTO;
import org.fiap.domain.entity.ClienteEntity;
import org.fiap.infra.exceptions.ObjectNotFoundException;
import org.fiap.infra.exceptions.RecordAlreadyExistsException;
import org.fiap.infra.repository.postgres.ClienteCustomRepository;
import org.fiap.infra.repository.postgres.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    @Mock
    private ClienteCustomRepository customRepository;

    @InjectMocks
    private ClienteService clienteService;

    private ClienteDTO clienteDTO;
    private ClienteEntity clienteEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        clienteDTO.setNome("João da Silva");
        clienteDTO.setCpf("12345678901");
        clienteDTO.setDataNascimento(LocalDate.of(1990, 1, 1));
        clienteDTO.setRua("Rua Teste");
        clienteDTO.setNumero(123);
        clienteDTO.setCep("12345000");
        clienteDTO.setCidade("São Paulo");
        clienteDTO.setEstado("SP");
        clienteDTO.setComplemento("Apt 101");

        clienteEntity = new ClienteEntity(clienteDTO);
    }

    @Test
    void testFindAll() {
        when(repository.findAll()).thenReturn(List.of(clienteEntity));

        var result = clienteService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("João da Silva", result.get(0).getNome());
    }

    @Test
    void testFindById() {
        when(repository.findById(1L)).thenReturn(Optional.of(clienteEntity));

        var result = clienteService.findById(1L);

        assertNotNull(result);
        assertEquals("João da Silva", result.getNome());
    }

    @Test
    void testFindByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        var result = clienteService.findById(1L);

        assertNull(result);
    }

    @Test
    void testSave() {
        when(repository.findByCpf("12345678901")).thenReturn(null);
        when(repository.save(any(ClienteEntity.class))).thenReturn(clienteEntity);

        var result = clienteService.save(clienteDTO);

        assertNotNull(result);
        assertEquals("João da Silva", result.getNome());
    }

    @Test
    void testSaveWithExistingCpf() {
        when(repository.findByCpf("12345678901")).thenReturn(clienteEntity);

        assertThrows(RecordAlreadyExistsException.class, () -> clienteService.save(clienteDTO));
    }

    @Test
    void testUpdate() {
        when(repository.findById(1L)).thenReturn(Optional.of(clienteEntity));
        when(repository.save(any(ClienteEntity.class))).thenReturn(clienteEntity);

        clienteDTO.setNome("João Silva Atualizado");

        var result = clienteService.update(1L, clienteDTO);

        assertNotNull(result);
        assertEquals("João Silva Atualizado", result.getNome());
    }

    @Test
    void testUpdateNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> clienteService.update(1L, clienteDTO));
    }

    @Test
    void testDelete() {
        when(repository.findById(1L)).thenReturn(Optional.of(clienteEntity));

        clienteService.delete(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void testDeleteNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> clienteService.delete(1L));
    }

    @Test
    void testFindByCpf() {
        when(repository.findByCpf("12345678901")).thenReturn(clienteEntity);

        var result = clienteService.findByCpf("12345678901");

        assertNotNull(result);
        assertEquals("João da Silva", result.getNome());
    }

    @Test
    void testFindByCpfNotFound() {
        when(repository.findByCpf("12345678901")).thenReturn(null);
        assertThrows(ObjectNotFoundException.class, () -> clienteService.findByCpf("12345678901"));
    }

    @Test
    void testBuscaPaginada() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ClienteDTO> page = new PageImpl<>(List.of(clienteDTO), pageable, 1);
        when(customRepository.findAllByCriteria(any(), eq(pageable), any(), any())).thenReturn(page);

        var result = clienteService.buscaPaginada(clienteDTO, 0, 10, "nome", "asc");

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("João da Silva", result.getContent().get(0).getNome());
    }
}
