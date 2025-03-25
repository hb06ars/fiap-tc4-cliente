package org.fiap.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.fiap.app.rest.request.ClienteRequest;
import org.fiap.domain.entity.ClienteEntity;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ClienteDTO {

    private Long id;
    private String nome;
    private String cpf;
    private String celular;

    public ClienteDTO(ClienteEntity clienteEntity) {
        this.id = clienteEntity.getId();
        this.nome = clienteEntity.getNome();
        this.cpf = clienteEntity.getCpf();
        this.celular = clienteEntity.getCelular();
    }

    public ClienteDTO(ClienteRequest request) {
        this.id = request.getId();
        this.nome = request.getNome();
        this.cpf = request.getCpf();
        this.celular = request.getCelular();
    }
}