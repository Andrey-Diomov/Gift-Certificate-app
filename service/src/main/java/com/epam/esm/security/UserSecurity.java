package com.epam.esm.security;

import com.epam.esm.entity.ERole;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("userSecurity")
public class UserSecurity {
    public boolean hasUserId(Authentication auth, String id) {
        String gottenId = auth.getCredentials().toString();
        return id.equals(gottenId);
    }

    public boolean hasAdminRole(Authentication auth) {
        return auth.getAuthorities()
                .stream()
                .anyMatch(r -> r.getAuthority()
                        .equals(ERole.ADMIN.toString()));
    }

    public String getUserId(Authentication auth) {
        return auth.getCredentials().toString();
    }
}