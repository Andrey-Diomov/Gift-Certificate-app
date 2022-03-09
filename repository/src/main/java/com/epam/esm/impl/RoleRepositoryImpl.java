package com.epam.esm.impl;

import com.epam.esm.entity.ERole;
import com.epam.esm.entity.impl.Role;
import com.epam.esm.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@AllArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    private static final String JPQL_REQUEST_TO_SELECT_BY_NAME = "SELECT r FROM Role r WHERE r.name = :name";
    private static final String NAME = "name";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role get(String name) {
        return entityManager.createQuery(JPQL_REQUEST_TO_SELECT_BY_NAME, Role.class)
                .setParameter(NAME, name)
                .getSingleResult();
    }
}
