package com.epam.esm.dto.security;

import lombok.Data;
import javax.validation.constraints.Size;

@Data
public class SignUpRequest {
    @Size(min = 3, max = 15)
    private String login;

    @Size(min = 5, max = 10)
    private String password;
}