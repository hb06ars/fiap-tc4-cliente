package org.fiap.app.service.postgres;

import org.fiap.domain.dto.ClienteDTO;
import org.fiap.domain.entity.ClienteEntity;
import org.fiap.domain.util.AjustesString;
import org.fiap.infra.exceptions.ObjectNotFoundException;
import org.fiap.infra.exceptions.RecordAlreadyExistsException;
import org.fiap.infra.repository.postgres.ClienteCustomRepository;
import org.fiap.infra.repository.postgres.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository repository;
    private final ClienteCustomRepository customRepository;

    @Autowired
    public ClienteService(ClienteRepository repository, ClienteCustomRepository customRepository) {
        this.repository = repository;
        this.customRepository = customRepository;
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
    public ClienteDTO update(Long id, ClienteDTO clienteSalvar) {
        ClienteEntity clienteExistente = repository.findById(id).orElse(null);
        if (clienteExistente != null &&
                clienteExistente.getCpf().equals(Objects.requireNonNull(clienteExistente).getCpf())) {
            clienteExistente.setNome(clienteSalvar.getNome());
            return new ClienteDTO(repository.save(clienteExistente));
        } else {
            throw new ObjectNotFoundException("Cliente " + id + " não encontrado.");
        }
    }

    @Transactional
    public void delete(Long id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
        } else {
            throw new ObjectNotFoundException("Cliente com ID: " + id + ", não encontrado.");
        }
    }

    public ClienteDTO findByCpf(String cpf) {
        var clienteEncontrado = repository.findByCpf(
                cpf != null && !cpf.isBlank() ? AjustesString.removerTracosCpf(cpf.trim()) : null);

        if (clienteEncontrado == null)
            throw new ObjectNotFoundException("Cliente não encontrado.");
        return new ClienteDTO(clienteEncontrado);
    }

    public Page<ClienteDTO> buscaPaginada(ClienteDTO dto, int page, int size, String sortField, String sortDirection) {
        Pageable pageable = PageRequest.of(page, size);
        return customRepository.findAllByCriteria(dto, pageable, sortField, sortDirection);
    }
}

