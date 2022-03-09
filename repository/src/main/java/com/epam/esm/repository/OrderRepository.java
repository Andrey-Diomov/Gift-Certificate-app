package com.epam.esm.repository;

import com.epam.esm.entity.impl.Order;
import java.util.Optional;

public interface OrderRepository {

    Order create (Order order);

    Optional<Order> get(Long id);
}
