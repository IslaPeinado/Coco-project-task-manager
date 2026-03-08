package com.coco.security.jwt;

import com.coco.modules.user.infra.persistence.UserJpaRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class JwtServiceTest {

    private static final String SECRET = "TEST_SECRET_032c37e71fc74611174dfb03d65c3e941767a794377a0eaa3aba06da064951f6";
    private static final String ISSUER = "coco-test";

    @Test
    void generateToken_includesProjectMembershipModelAndTechnicalAuthority() {
        JwtService jwtService = new JwtService(
                new JwtProperties(SECRET, 3600, ISSUER),
                mock(UserJpaRepository.class)
        );

        String token = jwtService.generateToken("42");
        List<SimpleGrantedAuthority> authorities = jwtService.extractAuthorities(token);

        var claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)))
                .requireIssuer(ISSUER)
                .build()
                .parseClaimsJws(token)
                .getBody();

        assertEquals("PROJECT_MEMBERSHIP", claims.get("authz_model", String.class));
        assertTrue(authorities.contains(new SimpleGrantedAuthority("AUTHENTICATED")));
    }

    @Test
    void extractAuthorities_whenTokenHasNoAuthoritiesClaim_returnsDefaultAuthority() {
        JwtService jwtService = new JwtService(
                new JwtProperties(SECRET, 3600, ISSUER),
                mock(UserJpaRepository.class)
        );

        String legacyToken = Jwts.builder()
                .setSubject("7")
                .setIssuer(ISSUER)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(3600)))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();

        List<SimpleGrantedAuthority> authorities = jwtService.extractAuthorities(legacyToken);
        assertEquals(List.of(new SimpleGrantedAuthority("AUTHENTICATED")), authorities);
    }
}
