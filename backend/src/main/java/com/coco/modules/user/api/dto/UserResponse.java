package com.coco.modules.user.api.dto;

import java.time.OffsetDateTime;

public record UserResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        String imageUrl,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
