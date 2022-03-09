package com.epam.esm.service.impl;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.impl.Order;
import com.epam.esm.entity.impl.User;
import com.epam.esm.exception.ByIdUserNotFoundException;
import com.epam.esm.impl.UserRepositoryImpl;
import com.epam.esm.mapper.OrderServiceMapper;
import com.epam.esm.mapper.TagServiceMapper;
import com.epam.esm.mapper.UserServiceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {
    private final UserRepositoryImpl userMock = mock(UserRepositoryImpl.class);
    private final UserServiceImpl userService = new UserServiceImpl(userMock,
            new UserServiceMapper(new ModelMapper()), new OrderServiceMapper(new ModelMapper()),
            new TagServiceMapper(new ModelMapper()));

    private Order order;
    private User user;
    private UserDTO userDTO;

    @BeforeEach
    public void init() {
        order = new Order();
        order.setId(15L);
        order.setPrice(12.0);

        user = new User();
        user.setId(5L);
        user.setLogin("Andrey");

        userDTO = new UserDTO();
        userDTO.setId(5L);
        userDTO.setLogin("Andrey");
    }

    @Test
    public void testGetById() {
        long userId = 5;

        when(userMock.get(userId)).thenReturn(Optional.of(user));
        UserDTO actualUser = userService.get(userId);

        assertEquals(userDTO.getId(), actualUser.getId());
        assertEquals(userDTO.getLogin(), actualUser.getLogin());
    }

    @Test
    public void testGetByIdToNonExistingId() {
        long nonExistingId = 1000;
        when(userMock.get(nonExistingId)).thenReturn(Optional.empty());
        assertThrows(ByIdUserNotFoundException.class, () -> userService.get(nonExistingId));
    }

    @Test
    public void testGetAll() {
        int pageNumber = 1;
        int pageSize = 1;
        List<User> users = List.of(user);

        when(userMock.getAll(pageNumber, pageSize)).thenReturn(users);
        List<UserDTO> actualUserDTOs = userService.getAll(pageNumber, pageSize);

        verify(userMock, times(1)).getAll(pageNumber, pageSize);
        assertEquals(users.size(), actualUserDTOs.size());
    }

    @Test
    public void getOrdersByUserId() {
        long userId = 1;
        int pageNumber = 1;
        int pageSize = 1;
        List<Order> orders = List.of(order);

        when(userMock.get(userId)).thenReturn(Optional.of(user));
        when(userMock.getOrdersByUserId(userId, pageNumber, pageSize)).thenReturn(orders);

        List<OrderDTO> actualOrderDTOs = userService.getOrdersByUserId(userId, pageNumber, pageSize);

        assertEquals(orders.size(), actualOrderDTOs.size());
        assertEquals(orders.get(0).getPrice(), actualOrderDTOs.get(0).getPrice());
    }

    @Test
    public void testGetOrdersByUserIdToNonExistingId() {
        long nonExistingId = 1000;
        when(userMock.get(nonExistingId)).thenReturn(Optional.empty());
        assertThrows(ByIdUserNotFoundException.class, () -> userService.get(nonExistingId));
    }
}