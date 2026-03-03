package com.coco.modules.user.api.dto;

public record AuthResponse(
        String accessToken,
        String tokenType,
        long expiresIn
) {
}
