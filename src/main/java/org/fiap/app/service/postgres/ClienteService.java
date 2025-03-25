package org.fiap.app.service.postgres;

import org.fiap.domain.dto.ClienteDTO;
import org.fiap.domain.entity.ClienteEntity;
import org.fiap.domain.util.AjustesString;
import org.fiap.infra.exceptions.RecordAlreadyExistsException;
import org.fiap.infra.repository.postgres.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    @Autowired
    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<ClienteDTO> findAll() {
        return repository.findAll().stream().map(ClienteDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public ClienteDTO findById(Long id) {
        Optional<ClienteEntity> obj = repository.findById(id);
        return obj.map(ClienteDTO::new).orElse(null);
    }

    @Transactional
    public ClienteDTO save(ClienteDTO dto) {
        ClienteEntity clienteExiste = repository.findByCpf(
                AjustesString.removerTracosCpf(dto.getCpf()));
        if (clienteExiste == null)
            return new ClienteDTO(repository.save(new ClienteEntity(dto)));
        throw new RecordAlreadyExistsException("O CPF já existe no sistema.");
    }

    @Transactional
    public ClienteDTO update(Long id, ClienteDTO usuarioSalvar) {
        ClienteEntity usuarioExistente = repository.findById(id).orElse(null);
        var registroRepetido = repository.findByCpf(
                AjustesString.removerTracosCpf(usuarioSalvar.getCpf()));
        if (usuarioExistente != null && (registroRepetido == null ||
                registroRepetido.getId().equals(Objects.requireNonNull(usuarioExistente).getId()))) {
            usuarioExistente.setNome(usuarioSalvar.getNome());
            usuarioExistente.setCpf(usuarioSalvar.getCpf());
            usuarioExistente.setCelular(usuarioSalvar.getCelular());
            return new ClienteDTO(repository.save(usuarioExistente));
        } else {
            throw new RuntimeException("Usuário " + id + " não encontrado.");
        }
    }

    @Transactional
    public void delete(Long id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("Usuário com ID: " + id + ", não encontrado.");
        }
    }

    public ClienteDTO findByCpf(String email, String celular) {
        var usuarioEncontrado = repository.findByCpf(
                celular != null && !celular.isBlank() ? AjustesString.removerCaracteresCel(celular.trim()) : null);

        if (usuarioEncontrado == null)
            throw new RuntimeException("Usuário não encontrado.");
        return new ClienteDTO(usuarioEncontrado);
    }
}

