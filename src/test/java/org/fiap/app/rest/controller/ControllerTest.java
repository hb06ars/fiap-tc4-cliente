package org.fiap.app.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fiap.app.rest.request.ClienteRequest;
import org.fiap.app.service.postgres.ClienteService;
import org.fiap.domain.dto.ClienteDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private Controller clienteController;

    private ClienteDTO clienteDTO;
    private ClienteRequest clienteRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();

        clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        clienteDTO.setNome("João da Silva");
        clienteDTO.setCpf("12345678901");
        clienteDTO.setCidade("São Paulo");

        clienteRequest = new ClienteRequest();
        clienteRequest.setNome("João da Silva");
        clienteRequest.setCpf("12345678901");
        clienteRequest.setCidade("São Paulo");
        clienteRequest.setDtAtualizacao(LocalDate.now());
        clienteRequest.setDtCriacao(LocalDate.now());
    }

    @Test
    void testCadastro() throws Exception {
        when(clienteService.save(any(ClienteDTO.class))).thenReturn(clienteDTO);

        mockMvc.perform(post("/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(clienteRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("João da Silva")))
                .andExpect(jsonPath("$.cpf", is("12345678901")));
    }

    @Test
    void testAtualizar() throws Exception {
        when(clienteService.update(eq(1L), any(ClienteDTO.class))).thenReturn(clienteDTO);

        mockMvc.perform(put("/cliente/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(clienteRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("João da Silva")))
                .andExpect(jsonPath("$.cpf", is("12345678901")));
    }

    @Test
    void testBuscarPorId() throws Exception {
        when(clienteService.findById(1L)).thenReturn(clienteDTO);

        mockMvc.perform(get("/cliente/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("João da Silva")))
                .andExpect(jsonPath("$.cpf", is("12345678901")));
    }

    @Test
    void testBuscarPorCpf() throws Exception {
        when(clienteService.findByCpf("12345678901")).thenReturn(clienteDTO);

        mockMvc.perform(get("/cliente")
                        .param("cpf", "12345678901"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("João da Silva")))
                .andExpect(jsonPath("$.cpf", is("12345678901")));
    }

    @Test
    void testBuscaPaginada() throws Exception {
        Page<ClienteDTO> page = new PageImpl<>(List.of(clienteDTO), PageRequest.of(0, 6), 1);
        when(clienteService.buscaPaginada(any(ClienteDTO.class), eq(0), eq(6), eq("nome"), eq("asc"))).thenReturn(page);

        mockMvc.perform(get("/cliente/filtro")
                        .param("page", "0")
                        .param("size", "6")
                        .param("sortField", "nome")
                        .param("sortDirection", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nome", is("João da Silva")))
                .andExpect(jsonPath("$.content[0].cpf", is("12345678901")));
    }

    @Test
    void testDeletar() throws Exception {
        doNothing().when(clienteService).delete(1L);

        mockMvc.perform(delete("/cliente/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Deleção realizada com sucesso.")));
    }
}
