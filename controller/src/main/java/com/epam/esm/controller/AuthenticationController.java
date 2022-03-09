package com.epam.esm.controller;

import com.epam.esm.dto.security.AuthResponse;
import com.epam.esm.dto.security.LoginRequest;
import com.epam.esm.dto.security.SignUpRequest;
import com.epam.esm.exception.EmptyBodyRequestException;
import com.epam.esm.exception.WrongDataException;
import com.epam.esm.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/authentication")
public class AuthenticationController {
    private final AuthenticationService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody(required = false) @Valid LoginRequest request,
                              BindingResult bindingResult) {
        if (request == null) {
            throw new EmptyBodyRequestException();
        }
        if (bindingResult.hasErrors()) {
            throw new WrongDataException(bindingResult);
        }
        return authService.login(request);
    }

    @PostMapping("/signUp")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@RequestBody @Valid SignUpRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new WrongDataException(bindingResult);
        }
        return authService.register(request);
    }
}