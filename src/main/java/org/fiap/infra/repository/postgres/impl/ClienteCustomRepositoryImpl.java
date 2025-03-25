package org.fiap.infra.repository.postgres.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.fiap.domain.dto.ClienteDTO;
import org.fiap.domain.entity.ClienteEntity;
import org.fiap.infra.repository.postgres.ClienteCustomRepository;
import org.fiap.infra.repository.postgres.ClienteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ClienteCustomRepositoryImpl implements ClienteCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final ClienteRepository clienteRepository;

    public ClienteCustomRepositoryImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Page<ClienteDTO> findAllByCriteria(ClienteDTO dto, Pageable pageable, String sortField, String sortDirection) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ClienteEntity> query = cb.createQuery(ClienteEntity.class);
        Root<ClienteEntity> root = query.from(ClienteEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(dto)) {
            if (dto.getNome() != null) {
                predicates.add(cb.like(root.get("nome"), "%" + dto.getNome() + "%"));
            }
            if (dto.getCpf() != null) {
                predicates.add(cb.equal(root.get("cpf"), dto.getCpf()));
            }
            if (dto.getCidade() != null) {
                predicates.add(cb.like(root.get("cidade"), "%" + dto.getCidade() + "%"));
            }
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        if (sortField != null && !sortField.isEmpty() && sortDirection != null && !sortDirection.isEmpty()) {
            if (sortDirection.equalsIgnoreCase("asc")) {
                query.orderBy(cb.asc(root.get(sortField)));
            } else {
                query.orderBy(cb.desc(root.get(sortField)));
            }
        }

        List<ClienteEntity> resultList = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        List<ClienteDTO> clienteDTOs = new ArrayList<>();
        for (ClienteEntity cliente : resultList) {
            clienteDTOs.add(new ClienteDTO(cliente));
        }

        return new PageImpl<>(clienteDTOs, pageable, clienteDTOs.size());
    }
}
