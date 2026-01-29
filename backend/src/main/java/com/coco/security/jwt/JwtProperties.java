package com.coco.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "coco.jwt")
public record JwtProperties(
        String secret,
        long expirationSeconds,
        String issuer
) { }
