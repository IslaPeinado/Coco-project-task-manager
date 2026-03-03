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

@Service
public class JwtService {

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
        parseClaims(token);
        return List.of();
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
}
