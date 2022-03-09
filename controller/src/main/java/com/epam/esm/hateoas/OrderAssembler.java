package com.epam.esm.hateoas;

import com.epam.esm.controller.OrderController;
import com.epam.esm.dto.OrderDTO;
import org.springframework.stereotype.Component;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderAssembler {
    public OrderDTO addLink(OrderDTO orderDTO) {
        return orderDTO.add(linkTo(methodOn(OrderController.class)
                .getById(orderDTO.getId().toString())).withSelfRel());
    }

    public List<OrderDTO> addLink(List<OrderDTO> orderDTOs) {
        return orderDTOs.stream().peek(this::addLink).collect(toList());
    }
}

