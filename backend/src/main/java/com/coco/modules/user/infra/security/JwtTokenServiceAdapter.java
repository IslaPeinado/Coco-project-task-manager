package com.coco.modules.user.infra.security;

import com.coco.modules.user.application.port.TokenServicePort;
import com.coco.security.jwt.JwtService;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenServiceAdapter implements TokenServicePort {

    private final JwtService jwtService;

    public JwtTokenServiceAdapter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public String generateAccessToken(String subject) {
        return jwtService.generateToken(subject);
    }

    @Override
    public long getAccessTokenExpirationSeconds() {
        return jwtService.getExpirationSeconds();
    }
}
