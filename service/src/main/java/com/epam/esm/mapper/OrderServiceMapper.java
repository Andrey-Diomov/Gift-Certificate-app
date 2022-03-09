package com.epam.esm.mapper;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.entity.impl.Order;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderServiceMapper {
    private final ModelMapper mapper;

    public OrderDTO convertToDTO(Order order) {
        return mapper.map(order, OrderDTO.class);
    }
}