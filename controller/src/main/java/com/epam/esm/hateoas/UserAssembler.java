package com.epam.esm.hateoas;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.UserDTO;
import org.springframework.stereotype.Component;
import java.util.List;
import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserAssembler {
    public UserDTO addLink(UserDTO userDTO) {
        return userDTO.add(linkTo(methodOn(UserController.class)
                .getById(userDTO.getId().toString())).withSelfRel());
    }

    public List<UserDTO> addLink(List<UserDTO> userDTOs) {
        return userDTOs.stream().peek(this::addLink).collect(toList());
    }
}
