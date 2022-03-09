package com.epam.esm.security;

import com.epam.esm.entity.impl.User;
import com.epam.esm.exception.ByLoginUserNotFoundException;
import com.epam.esm.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) {
        User user = userRepository.get(login)
                .orElseThrow(() -> new ByLoginUserNotFoundException(login));
        return UserDetailsImpl.build(user);
    }
}
