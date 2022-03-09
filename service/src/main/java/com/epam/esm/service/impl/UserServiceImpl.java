package com.epam.esm.service.impl;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.exception.ByIdUserNotFoundException;
import com.epam.esm.mapper.OrderServiceMapper;
import com.epam.esm.mapper.TagServiceMapper;
import com.epam.esm.mapper.UserServiceMapper;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserServiceMapper mapper;
    private final OrderServiceMapper orderMapper;
    private final TagServiceMapper tagMapper;

    @Override
    public List<UserDTO> getAll(int pageNumber, int pageSize) {
        return userRepository.getAll(pageNumber, pageSize)
                .stream()
                .map(mapper::convertToDTO)
                .collect(toList());
    }

    @Override
    public UserDTO get(long id) {
        return mapper.convertToDTO(userRepository.get(id)
                .orElseThrow(() -> new ByIdUserNotFoundException(id)));
    }

    @Override
    public List<OrderDTO> getOrdersByUserId(long id, int pageNumber, int pageSize) {
        mapper.convertToDTO(userRepository.get(id)
                .orElseThrow(() -> new ByIdUserNotFoundException(id)));

        return userRepository.getOrdersByUserId(id, pageNumber, pageSize)
                .stream()
                .map(orderMapper::convertToDTO)
                .collect(toList());
    }

    @Override
    public TagDTO getMostUsedTagWithHighestCostOrders(long id) {
        mapper.convertToDTO(userRepository.get(id)
                .orElseThrow(() -> new ByIdUserNotFoundException(id)));

        return tagMapper.convertToDTO(userRepository.getMostUsedTagWithHighestCostOrders(id));
    }
}