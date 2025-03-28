package org.fiap.infra.repository.postgres.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.fiap.domain.dto.ClienteDTO;
import org.fiap.domain.entity.ClienteEntity;
import org.fiap.infra.repository.postgres.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ClienteCustomRepositoryImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private CriteriaQuery<ClienteEntity> criteriaQuery;

    @Mock
    private Root<ClienteEntity> root;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteCustomRepositoryImpl clienteCustomRepositoryImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllByCriteria() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNome("João");
        clienteDTO.setCpf("12345678901");

        List<ClienteEntity> clienteEntities = new ArrayList<>();
        ClienteEntity cliente = new ClienteEntity();
        cliente.setNome("João");
        cliente.setCpf("12345678901");
        clienteEntities.add(cliente);

        PageRequest pageable = PageRequest.of(0, 10);

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(ClienteEntity.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(ClienteEntity.class)).thenReturn(root);

        Predicate predicateMock = mock(Predicate.class);
        when(criteriaBuilder.like(root.get("nome"), "%João%")).thenReturn(predicateMock);
        when(criteriaBuilder.equal(root.get("cpf"), "12345678901")).thenReturn(predicateMock);

        when(entityManager.createQuery(criteriaQuery)).thenReturn(mock(TypedQuery.class));
        when(entityManager.createQuery(criteriaQuery).getResultList()).thenReturn(clienteEntities);

        Page<ClienteDTO> expectedPage = new PageImpl<>(List.of(new ClienteDTO(cliente)), pageable, 1);

        Page<ClienteDTO> result = clienteCustomRepositoryImpl.findAllByCriteria(clienteDTO, pageable, "nome", "asc");

        verify(entityManager).getCriteriaBuilder();
        verify(entityManager).createQuery(criteriaQuery);
        assert (result.getContent().size() == 1);
        assert (result.getContent().get(0).getNome().equals("João"));
    }
}
