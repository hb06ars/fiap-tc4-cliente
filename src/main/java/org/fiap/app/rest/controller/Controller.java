package org.fiap.app.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.fiap.app.rest.request.ClienteRequest;
import org.fiap.app.service.postgres.ClienteService;
import org.fiap.domain.dto.ClienteDTO;
import org.fiap.domain.dto.MessageDTO;
import org.fiap.domain.util.HttpStatusCodes;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/cliente")
@Slf4j
public class Controller {

    private final ClienteService service;

    public Controller(ClienteService service) {
        this.service = service;
    }

    @Operation(summary = "Cadastrar Cliente",
            description = "Criação do Cliente.")
    @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Cadastro realizado com sucesso.")
    @PostMapping
    public ResponseEntity<ClienteDTO> cadastro(@Valid @RequestBody ClienteRequest request) {
        log.info("requisição para cadastrar cliente foi efetuada");
        return ResponseEntity.ok(service.save(new ClienteDTO(request)));
    }

    @Operation(summary = "Atualizar Cliente",
            description = "Atualização do Cliente.")
    @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Atualização realizada com sucesso.")
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizar(@PathVariable(name = "id") Long id,
                                                @Valid @RequestBody ClienteRequest request) {
        log.info("requisição para atualizar cliente foi efetuada");
        return ResponseEntity.ok(service.update(id, new ClienteDTO(request)));
    }

    @Operation(summary = "Busca paginada",
            description = "Busca todos os registros salvos no MongoDB.")
    @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Busca realizada com sucesso.")
    @GetMapping("/filtro")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<ClienteDTO>> buscaPaginada(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) String cidade,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(defaultValue = "nome") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        ClienteDTO dto = ClienteDTO.builder()
                .nome(nome)
                .cpf(cpf)
                .cidade(cidade)
                .build();

        return ResponseEntity.ok(service.buscaPaginada(dto, page, size, sortField, sortDirection));
    }

    @Operation(summary = "Buscar Cliente",
            description = "Buscar o Cliente.")
    @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Busca realizada com sucesso.")
    @GetMapping
    public ResponseEntity<ClienteDTO> buscar(
            @RequestParam(name = "cpf", required = true) String cpf
    ) {
        log.info("requisição para buscar cliente foi efetuada");
        return ResponseEntity.ok(service.findByCpf(cpf));
    }

    @Operation(summary = "Buscar Cliente por ID",
            description = "Buscar o Cliente por ID.")
    @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Busca realizada com sucesso.")
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarPorId(
            @PathVariable(name = "id") Long id
    ) {
        log.info("requisição para buscar cliente por id foi efetuada");
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Deletar Cliente",
            description = "Deleção do Cliente.")
    @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Deleção realizada com sucesso.")
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDTO> deletar(@PathVariable(name = "id") Long id) {
        log.info("requisição para deleção do cliente foi efetuada");
        service.delete(id);
        return ResponseEntity.ok(MessageDTO.builder().message("Deleção realizada com sucesso.").build());
    }

}