package com.epam.esm.service;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> getAll(int pageNumber, int pageSize);

    UserDTO get(long id);

    List<OrderDTO> getOrdersByUserId(long id, int pageNumber, int pageSize);

    TagDTO getMostUsedTagWithHighestCostOrders(long id);
}