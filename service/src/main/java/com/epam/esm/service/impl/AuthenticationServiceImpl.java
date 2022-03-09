package com.epam.esm.service.impl;

import com.epam.esm.dto.security.AuthResponse;
import com.epam.esm.dto.security.LoginRequest;
import com.epam.esm.dto.security.SignUpRequest;
import com.epam.esm.entity.ERole;
import com.epam.esm.entity.impl.Role;
import com.epam.esm.entity.impl.User;
import com.epam.esm.exception.ByLoginUserNotFoundException;
import com.epam.esm.exception.InvalidPasswordException;
import com.epam.esm.exception.UserExistsException;
import com.epam.esm.repository.RoleRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.security.UserDetailsImpl;
import com.epam.esm.security.jwt.JwtUtils;
import com.epam.esm.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final String EMPTY_TOKEN = "";

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional
    @Override
    public AuthResponse register(SignUpRequest request) {
        if (userRepository.get(request.getLogin()).isPresent()) {
            throw new UserExistsException(request.getLogin());
        }

        User user = userRepository.create(User.builder()
                .login(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(List.of(roleRepository.get(ERole.USER.toString())))
                .build());

        return new AuthResponse().builder()
                .id(user.getId())
                .login(user.getLogin())
                .roles(user.getRoles().stream().map(Role::getName).collect(toList()))
                .jwtToken(EMPTY_TOKEN)
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        userRepository.get(request.getLogin())
                .orElseThrow(() -> new ByLoginUserNotFoundException(request.getLogin()));
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new InvalidPasswordException();
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(toList());

        return AuthResponse.builder()
                .id(userDetails.getId())
                .login(userDetails.getLogin())
                .roles(roles)
                .jwtToken(jwt)
                .build();
    }
}
