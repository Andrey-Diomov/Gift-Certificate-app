package com.epam.esm.impl;

import com.epam.esm.entity.impl.Certificate;
import com.epam.esm.entity.impl.Tag;
import com.epam.esm.parameter.ParametersCertificateQuery;
import com.epam.esm.repository.CertificateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.*;
import static java.lang.String.format;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Repository
@AllArgsConstructor
public class CertificateRepositoryImpl implements CertificateRepository {
    private static final String JPQL_REQUEST_TO_SELECT_BY_NAME = "SELECT c FROM Certificate c WHERE c.name = :name";

    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String LIKE_OPERATOR_FORMAT = "%%%s%%";
    private static final String DEFAULT_SORTING_MODE = "ASC";
    private static final String CREATED = "created";
    private static final String TAGS = "tags";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Certificate> get(long id) {
        return Optional.ofNullable(entityManager.find(Certificate.class, id));
    }

    @Override
    public Optional<Certificate> get(String name) {
        return entityManager.createQuery(JPQL_REQUEST_TO_SELECT_BY_NAME, Certificate.class)
                .setParameter(NAME, name)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Transactional
    @Override
    public Certificate create(Certificate certificate) {
        return entityManager.merge(certificate);
    }

    @Transactional
    @Override
    public void delete(long id) {
        Certificate certificate = entityManager.find(Certificate.class, id);
        entityManager.remove(certificate);
    }

    @Override
    public Certificate patch(Certificate certificate, Certificate patch) {

        String name = patch.getName();
        if (name != null) {
            certificate.setName(name);
        }
        String description = patch.getDescription();
        if (description != null) {
            certificate.setDescription(description);
        }
        Double price = patch.getPrice();
        if (price != null) {
            certificate.setPrice(price);
        }
        Integer duration = patch.getDuration();
        if (duration != null) {
            certificate.setDuration(duration);
        }
        return entityManager.merge(certificate);
    }

    @Override
    public List<Certificate> get(ParametersCertificateQuery parameters, int pageNumber, int pageSize) {
        CriteriaQuery<Certificate> criteriaQuery = createCriteriaQuery(parameters);
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(pageNumber * pageSize - pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    private CriteriaQuery<Certificate> createCriteriaQuery(
            ParametersCertificateQuery parameters) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Certificate> query = builder.createQuery(Certificate.class);
        Root<Certificate> root = query.from(Certificate.class);

        List<Predicate> predicates = new ArrayList<>();

        if (isNotBlank(parameters.getName())) {
            predicates.add(builder.like(root.get(NAME),
                    format(LIKE_OPERATOR_FORMAT, parameters.getName())));
        }

        if (isNotBlank(parameters.getDescription())) {
            predicates.add(builder.like(root.get(DESCRIPTION),
                    format(LIKE_OPERATOR_FORMAT, parameters.getDescription())));
        }

        if (isNotEmpty(parameters.getTags())) {
            Join<Certificate, Tag> join = root.join(TAGS, JoinType.INNER);
            predicates.add(builder.in(join.get(NAME)).value(parameters.getTags()));
            query.groupBy(root);
            query.having(builder.equal(builder.count(root), parameters.getTags().size()));
        }

        if (isNotBlank(parameters.getSort())) {
            if (parameters.getSort().equalsIgnoreCase(DEFAULT_SORTING_MODE)) {
                query.orderBy(builder.asc(root.get(CREATED)));
            } else {
                query.orderBy(builder.desc(root.get(CREATED)));
            }
        }
        query.orderBy(builder.asc(root.get(NAME)));
        return query.select(root).where(builder.and(predicates.toArray(Predicate[]::new)));
    }
}