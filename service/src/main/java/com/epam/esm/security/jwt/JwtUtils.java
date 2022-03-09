package com.epam.esm.security.jwt;

import com.epam.esm.exception.UnauthorizedException;
import com.epam.esm.security.UserDetailsImpl;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Log4j2
@Component
public class JwtUtils {
    private static final String ROLES = "roles";

    private final String jwtSecret;
    private final int jwtExpirationMs;

    public JwtUtils(@Value("${jwt.token.secret}") String jwtSecret,
                    @Value("${jwt.token.expired}") int jwtExpirationMs) {
        this.jwtSecret = jwtSecret;
        this.jwtExpirationMs = jwtExpirationMs;
    }

    public String generateJwtToken(UserDetailsImpl userDetails) {
        return Jwts.builder()
                .setId(userDetails.getId().toString())
                .setSubject(userDetails.getUsername())
                .claim(ROLES, userDetails
                        .getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(toList()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public Claims extractClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        } catch (JwtException e) {
            log.debug(e);
            throw new UnauthorizedException();
        }
    }

    public List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
        return Optional.ofNullable(claims.get(ROLES))
                .map(claim -> (List<String>) claim)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(toList());
    }
}