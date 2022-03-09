package com.epam.esm.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import javax.validation.Valid;
import java.util.List;

@NoArgsConstructor
@Data
public class UserDTO extends RepresentationModel<UserDTO> {

    private Long id;

    private String login;

    @Valid
    private List<OrderDTO> orders;
}