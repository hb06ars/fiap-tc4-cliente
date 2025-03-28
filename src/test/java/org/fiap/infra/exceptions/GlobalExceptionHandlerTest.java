package org.fiap.infra.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fiap.app.rest.controller.Controller;
import org.fiap.app.service.postgres.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ClienteService service;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private Controller controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    void testHandleHttpMessageNotReadableException() throws Exception {
        mockMvc.perform(post("/cliente")
                        .contentType("application/json")
                        .content("{ invalid json }"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erro").value("Erro de formatação no JSON"))
                .andExpect(jsonPath("$.detalhe").exists())
                .andExpect(jsonPath("$.statusCode").value(400));
    }

    @Test
    void testHandleInvalidFormatException() throws Exception {
        mockMvc.perform(post("/cliente")
                        .contentType("application/json")
                        .content("{ \"campoInvalido\": \"valor\" }"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erro").value("Erro na validação de dados"))
                .andExpect(jsonPath("$.detalhe").exists())
                .andExpect(jsonPath("$.statusCode").value(400));
    }

    @Test
    void testHandleValidationExceptions() throws Exception {
        String body = """
                {
                    "clienteId": 0
                }
                """;

        mockMvc.perform(post("/cliente")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erro").value("Erro na validação de dados"))
                .andExpect(jsonPath("$.detalhe").value("O cpf não pode ser nulo. Por favor, forneça um valor."))
                .andExpect(jsonPath("$.campo").value("cpf"))
                .andExpect(jsonPath("$.statusCode").value(400));
    }

}
