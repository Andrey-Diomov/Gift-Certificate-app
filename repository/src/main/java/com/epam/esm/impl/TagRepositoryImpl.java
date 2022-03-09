package com.epam.esm.impl;

import com.epam.esm.entity.impl.Tag;
import com.epam.esm.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TagRepositoryImpl implements TagRepository {
    private static final String JPQL_REQUEST_TO_SELECT_ALL = "SELECT t FROM Tag t";
    private static final String JPQL_REQUEST_TO_SELECT_BY_NAME = "SELECT t FROM Tag t WHERE t.name = :name";

    private static final String NAME = "name";

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public List<Tag> getAll(int pageNumber, int pageSize) {
        return entityManager.createQuery(JPQL_REQUEST_TO_SELECT_ALL, Tag.class)
                .setFirstResult(pageNumber * pageSize - pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    @Override
    public Optional<Tag> get(long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public Tag create(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public void delete(long id) {
        Tag tag = entityManager.find(Tag.class, id);
        tag.getCertificates().forEach(cert -> cert.getTags().remove(tag));
        entityManager.remove(tag);
    }

    @Override
    public Optional<Tag> get(String name) {
        return entityManager.createQuery(JPQL_REQUEST_TO_SELECT_BY_NAME, Tag.class)
                .setParameter(NAME, name)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public Tag findOrCreate(Tag tag) {
        return get(tag.getName())
                .orElseGet(() -> create(tag));
    }
}