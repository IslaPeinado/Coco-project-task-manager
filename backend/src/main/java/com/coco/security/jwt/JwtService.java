package com.coco.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import com.coco.modules.user.infra.persistence.UserJpaRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {

    private static final String CLAIM_AUTHORITIES = "authorities";
    private static final String CLAIM_AUTHZ_MODEL = "authz_model";
    private static final String AUTHZ_MODEL_PROJECT_MEMBERSHIP = "PROJECT_MEMBERSHIP";
    private static final List<String> DEFAULT_AUTHORITIES = List.of("AUTHENTICATED");

    private final JwtProperties props;
    private final SecretKey key;
    private final UserJpaRepository userJpaRepository;

    public JwtService(JwtProperties props, UserJpaRepository userJpaRepository) {
        this.props = props;
        this.userJpaRepository = userJpaRepository;
        this.key = Keys.hmacShaKeyFor(props.secret().getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String subject) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(props.expirationSeconds());

        return Jwts.builder()
                .setClaims(Map.of(
                        CLAIM_AUTHORITIES, DEFAULT_AUTHORITIES,
                        CLAIM_AUTHZ_MODEL, AUTHZ_MODEL_PROJECT_MEMBERSHIP
                ))
                .setSubject(subject)
                .setIssuer(props.issuer())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(exp))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = parseClaims(token);
            Long userId = parseUserId(claims.getSubject());
            return userJpaRepository.existsById(userId);
        } catch (Exception ex) {
            return false;
        }
    }

    public String extractSubject(String token) {
        return parseClaims(token).getSubject();
    }

    public long getExpirationSeconds() {
        return props.expirationSeconds();
    }

    public List<SimpleGrantedAuthority> extractAuthorities(String token) {
        Claims claims = parseClaims(token);
        Object rawAuthorities = claims.get(CLAIM_AUTHORITIES);
        if (!(rawAuthorities instanceof List<?> listAuthorities)) {
            return defaultAuthorities();
        }

        List<SimpleGrantedAuthority> authorities = listAuthorities.stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .map(SimpleGrantedAuthority::new)
                .toList();

        return authorities.isEmpty() ? defaultAuthorities() : authorities;
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .requireIssuer(props.issuer())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Long parseUserId(String subject) {
        if (subject == null || subject.isBlank()) {
            throw new IllegalArgumentException("Token subject is empty");
        }
        return Long.valueOf(subject);
    }

    private List<SimpleGrantedAuthority> defaultAuthorities() {
        return DEFAULT_AUTHORITIES.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
