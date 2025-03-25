package org.fiap.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.fiap.domain.dto.ClienteDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Builder
@Entity
@Table(name = "usuario")
public class ClienteEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    @NotNull(message = "O nome não pode ser nulo. Por favor, forneça um valor.")
    private String nome;

    @Column(nullable = true, length = 255, unique = true)
    @NotNull(message = "O cpf não pode ser nulo. Por favor, forneça um valor.")
    private String cpf;

    @Column(nullable = true, length = 50, unique = true)
    @NotNull(message = "O celular não pode ser nulo. Por favor, forneça um valor.")
    private String celular;

    public ClienteEntity(Long id, String nome, String cpf, String celular) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.celular = celular;
    }

    public ClienteEntity(ClienteDTO dto) {
        this.id = dto.getId();
        this.nome = dto.getNome();
        this.cpf = dto.getCpf();
        this.celular = dto.getCelular();
    }
}
