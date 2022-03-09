package com.epam.esm.repository;

import com.epam.esm.entity.impl.Order;
import com.epam.esm.entity.impl.Tag;
import com.epam.esm.entity.impl.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> getAll(int pageNumber, int pageSize);

    Optional<User> get(long id);

    Optional<User> get(String login);

    List<Order> getOrdersByUserId(long id, int pageNumber, int pageSize);

    Tag getMostUsedTagWithHighestCostOrders(long id);

    User create(User user);
}
