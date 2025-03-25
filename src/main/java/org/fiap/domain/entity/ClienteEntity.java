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
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cliente")
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

    @Column(nullable = false)
    @NotNull(message = "A data de nascimento não pode ser nula.")
    private LocalDate dataNascimento;

    @Column(nullable = false, length = 255)
    @NotNull(message = "A rua não pode ser nula. Por favor, forneça um valor.")
    private String rua;

    @Column(nullable = false)
    @NotNull(message = "O número não pode ser nulo. Por favor, forneça um valor.")
    private Integer numero;

    @Column(nullable = false, length = 10)
    @NotNull(message = "O CEP não pode ser nulo. Por favor, forneça um valor.")
    private String cep;

    @Column(nullable = false, length = 100)
    @NotNull(message = "A cidade não pode ser nula. Por favor, forneça um valor.")
    private String cidade;

    @Column(nullable = false, length = 100)
    @NotNull(message = "O estado não pode ser nulo. Por favor, forneça um valor.")
    private String estado;

    @Column(nullable = true, length = 255)
    private String complemento;

    @Column(nullable = false, updatable = false)
    private LocalDate dtCriacao;

    @Column(nullable = false)
    private LocalDate dtAtualizacao;

    public ClienteEntity(ClienteDTO dto) {
        this.id = dto.getId();
        this.nome = dto.getNome();
        this.cpf = dto.getCpf();
        this.dataNascimento = dto.getDataNascimento();
        this.rua = dto.getRua();
        this.numero = dto.getNumero();
        this.cep = dto.getCep();
        this.cidade = dto.getCidade();
        this.estado = dto.getEstado();
        this.complemento = dto.getComplemento();
        this.dtCriacao = dto.getDtCriacao();
        this.dtAtualizacao = dto.getDtAtualizacao();
    }
}
