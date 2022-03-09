package com.epam.esm.mapper;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.impl.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserServiceMapper {
    private final ModelMapper mapper;

    public UserDTO convertToDTO(User user) {
        return mapper.map(user, UserDTO.class);
    }
}