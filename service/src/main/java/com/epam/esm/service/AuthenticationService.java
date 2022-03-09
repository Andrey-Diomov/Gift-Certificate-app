package com.epam.esm.service;

import com.epam.esm.dto.security.AuthResponse;
import com.epam.esm.dto.security.LoginRequest;
import com.epam.esm.dto.security.SignUpRequest;

public interface AuthenticationService {
    AuthResponse register(SignUpRequest request);

    AuthResponse login(LoginRequest request);
}
