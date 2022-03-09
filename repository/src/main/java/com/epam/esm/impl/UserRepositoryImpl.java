package com.epam.esm.impl;

import com.epam.esm.entity.impl.Order;
import com.epam.esm.entity.impl.Tag;
import com.epam.esm.entity.impl.User;
import com.epam.esm.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private static final String JPQL_REQUEST_TO_SELECT_ALL = "SELECT u FROM User u";
    private static final String JPQL_REQUEST_TO_SELECT_BY_ID = "SELECT u FROM User u WHERE u.id = :id";
    private static final String JPQL_REQUEST_TO_SELECT_BY_LOGIN = "SELECT u FROM User u WHERE u.login = :login";
    private static final String JPQL_REQUEST_TO_GET_MOST_USED_TAG_WITH_HIGHEST_COST_ORDERS = "SELECT Tag.id, Tag.name FROM Orders " +
            "JOIN Certificate ON Certificate.id = Orders.certificate_id " +
            "JOIN Relationships_certificates_and_tags ON Certificate.id = Relationships_certificates_and_tags.certificate_id " +
            "JOIN Tag ON Tag.id = Relationships_certificates_and_tags.tag_id " +
            "WHERE Orders.user_id =:id " +
            "GROUP BY Tag.id " +
            "ORDER BY SUM(Orders.price) DESC, COUNT(Tag.id) DESC LIMIT 1";

    private static final String ID = "id";
    private static final String LOGIN = "login";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAll(int pageNumber, int pageSize) {
        return entityManager.createQuery(JPQL_REQUEST_TO_SELECT_ALL, User.class)
                .setFirstResult(pageNumber * pageSize - pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    @Override
    public Optional<User> get(long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public Optional<User> get(String login) {
        return entityManager.createQuery(JPQL_REQUEST_TO_SELECT_BY_LOGIN, User.class)
                .setParameter(LOGIN, login)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public List<Order> getOrdersByUserId(long id, int pageNumber, int pageSize) {
        return entityManager.createQuery(JPQL_REQUEST_TO_SELECT_BY_ID, User.class)
                .setParameter(ID, id)
                .getSingleResult()
                .getOrders()
                .subList(pageNumber * pageSize - pageSize, pageNumber * pageSize);
    }

    @Override
    public Tag getMostUsedTagWithHighestCostOrders(long id) {
        return (Tag) entityManager.createNativeQuery(JPQL_REQUEST_TO_GET_MOST_USED_TAG_WITH_HIGHEST_COST_ORDERS, Tag.class)
                .setParameter(ID, id)
                .getSingleResult();
    }

    @Override
    public User create(User user) {
        entityManager.persist(user);
        return user;
    }
}